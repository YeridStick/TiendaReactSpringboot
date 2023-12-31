package com.company.inventory.service.impl;

import com.company.inventory.constantes.Constantes;
import com.company.inventory.dto.login.RolesGinDTO;
import com.company.inventory.dto.logout.RolesOutDTO;
import com.company.inventory.exception.ExcepcionPersonalizada;
import com.company.inventory.model.RolesUserEntity;
import com.company.inventory.repository.RolesRepository;
import com.company.inventory.response.MensajeResponseRest;
import com.company.inventory.service.RollService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RollServiceImpl implements RollService {
    private final RolesRepository roles;
    private  final ModelMapper modelMapper;

    public RollServiceImpl(RolesRepository roles, ModelMapper modelMapper) {
        this.roles = roles;
        this.modelMapper = modelMapper;
    }
    @Override
    public MensajeResponseRest createRoll(RolesGinDTO rolesGinDTO){
        MensajeResponseRest response = new MensajeResponseRest();
        try {
            Optional<RolesUserEntity> objetRoll = roles.findByCargo(rolesGinDTO.getCargo());

            if (objetRoll.isPresent() ) {
                response.setMetadata("El rol: " + rolesGinDTO.getCargo()  + " Ya Existe","Error al ingresar los datos");
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR).getBody();
            }

            RolesUserEntity rolesUserEntity = modelMapper.map(rolesGinDTO, RolesUserEntity.class);

            roles.save(rolesUserEntity);

            response.setMetadata(Constantes.TextRespuesta, "Rol creado exitosamente");
            return response;

        } catch (Exception e) {
            throw new ExcepcionPersonalizada(Constantes.TextRespuestaNo, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @Override
    public MensajeResponseRest listarRoles() {
        MensajeResponseRest response = new MensajeResponseRest();
        try {
            List<RolesUserEntity> listRoles = roles.findAll();

            List<RolesOutDTO> rolesOutDTOS = listRoles.stream()
                    .map(RolesUserEntity -> modelMapper.map(RolesUserEntity, RolesOutDTO.class))
                    .collect(Collectors.toList());

            response.getMensajeResponse().setRolesUser(rolesOutDTOS);
            response.setMetadata(Constantes.TextRespuesta, "Datos recuperados Correctamenete");
            return response;
        }catch (Exception e){
            throw new ExcepcionPersonalizada(Constantes.TextRespuestaNo, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
