package com.company.inventory.service.impl;

import com.company.inventory.constantes.Constantes;
import com.company.inventory.dto.login.UserGinDTO;
import com.company.inventory.dto.logout.UserOutDTO;
import com.company.inventory.exception.ExcepcionPersonalizada;
import com.company.inventory.model.RolesUserEntity;
import com.company.inventory.model.TipoEntidadEntity;
import com.company.inventory.model.UserEntity;
import com.company.inventory.repository.RolesRepository;
import com.company.inventory.repository.TipoEntidadRepository;
import com.company.inventory.repository.UserRepository;
import com.company.inventory.response.MensajeResponseRest;
import com.company.inventory.service.UsuarioService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioServiceImpl implements UsuarioService {
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final UserRepository userRepository;
    private final RolesRepository rolesRepository;
    private final TipoEntidadRepository tipoEntidadRepository;
    private  final ModelMapper modelMapper;

    public UsuarioServiceImpl(UserRepository userRepository, RolesRepository rolesRepository, TipoEntidadRepository tipoEntidadRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.rolesRepository = rolesRepository;
        this.tipoEntidadRepository = tipoEntidadRepository;
        this.modelMapper = modelMapper;
    }
    @Override
    public MensajeResponseRest crearUsuario(UserGinDTO userGinDTO){
        MensajeResponseRest response = new MensajeResponseRest();
        try{
            Optional<UserEntity> objetoUsuarioIdent = userRepository.findById(userGinDTO.getNumIdent());
            Optional<UserEntity> objetoUsuarioCorreo = userRepository.findByCorreo(userGinDTO.getCorreo());
            Optional<UserEntity> objetoUsuarioTelefono = userRepository.findByNumeroTelefono(userGinDTO.getNumeroTelefono());

            if (objetoUsuarioIdent.isPresent() || objetoUsuarioCorreo.isPresent() || objetoUsuarioTelefono.isPresent()) {
                response.setMetadata("Este numero de identificacion, ya se encuentra registrado: " + userGinDTO.getNumIdent() ,"Error al ingresar los datos");
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR).getBody();
            }

            TipoEntidadEntity tipoEntidad = tipoEntidadRepository.findBySigla(userGinDTO.getTipoEntidad())
                    .orElseThrow(() -> new ExcepcionPersonalizada("Tipo de entidad: " + userGinDTO.getTipoEntidad(), HttpStatus.NOT_FOUND));

            UserEntity userEntity = modelMapper.map(userGinDTO, UserEntity.class);
            userEntity.setPassword(passwordEncoder.encode(userGinDTO.getPassword().trim()));

            userEntity.setTipoEntidad(tipoEntidad);
            if (userEntity.getRolesUser() == null) {
                Optional<RolesUserEntity> objetoRoles = rolesRepository.findByCargo("User");

                if (objetoRoles.isPresent()) {
                    RolesUserEntity rolesUser = objetoRoles.get();
                    userEntity.setRolesUser(rolesUser);
                } else {
                    throw new ExcepcionPersonalizada("El rol 'User' no está presente en la base de datos", HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }

            userRepository.save(userEntity);

            response.getMensajeResponse().setUser(userEntity);
            response.setMetadata(Constantes.TextRespuesta, "Usuario Registrado Correctamente");
            return response;

        } catch (Exception e) {
            throw new ExcepcionPersonalizada(Constantes.TextRespuestaNo, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public MensajeResponseRest listarUsuarios() {
        MensajeResponseRest response = new MensajeResponseRest();
        try{
            List<UserEntity> tipoEntidadEntityList = userRepository.findAll();
            List<UserOutDTO> userOutDTOS = tipoEntidadEntityList.stream()
                            .map(userEntity -> modelMapper.map(userEntity, UserOutDTO.class))
                                    .collect(Collectors.toList());

            response.getMensajeResponse().setUsers(userOutDTOS);
            response.setMetadata(Constantes.TextRespuesta, "Datos recuperados Correctamenete");
            return response;
        }catch (Exception e) {
            throw new ExcepcionPersonalizada(Constantes.TextRespuestaNo, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @Override
    public MensajeResponseRest Userloging(String correo, String password) {
        MensajeResponseRest response = new MensajeResponseRest();
        try {
            Optional<UserEntity> user = userRepository.findByCorreo(correo);

            if (user.isPresent()) {
                String encodedPassword = passwordEncoder.encode(password.trim());
                user.get().setPassword(encodedPassword);

                response.getMensajeResponse().setUser(user.get());
                response.setMetadata(Constantes.TextRespuesta, "Usuario encontrado Exitosamente");
                return response;
            } else {
                response.setMetadata(Constantes.RespuestaNoExitosa, "Usuario no encontrado");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND).getBody();
            }
        } catch (Exception e) {
            throw new ExcepcionPersonalizada(Constantes.TextRespuestaNo, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
