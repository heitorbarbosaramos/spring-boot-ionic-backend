package com.heitor.cursomc.services.validation;

import com.heitor.cursomc.domain.Cliente;
import com.heitor.cursomc.domain.dto.ClienteDTO;
import com.heitor.cursomc.domain.dto.ClienteNewDTO;
import com.heitor.cursomc.enums.TipoCliente;
import com.heitor.cursomc.exceptions.FieldMessage;
import com.heitor.cursomc.reposotories.ClienteRepository;
import com.heitor.cursomc.services.utils.ValidaDocumentos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ClienteUpdateValidator implements ConstraintValidator<ClienteUpdate, ClienteDTO> {

    @Autowired
    private ClienteRepository repo;

    @Autowired
    private HttpServletRequest request;

    @Override
    public void initialize(ClienteUpdate constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(ClienteDTO objDto, ConstraintValidatorContext context) {

        Map<String, String> map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        Integer idURI = Integer.parseInt(map.get("idCliente"));

        List<FieldMessage> list = new ArrayList<>();

        Cliente aux = repo.findByEmail(objDto.getEmail());

        if(aux != null && !aux.getId().equals(idURI)){
            list.add(new FieldMessage("email", "email já cadastro"));
        }
        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getField())
                    .addConstraintViolation();
        }
        return list.isEmpty();
    }
}
