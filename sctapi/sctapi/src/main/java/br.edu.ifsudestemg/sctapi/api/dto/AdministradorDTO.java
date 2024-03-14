package br.edu.ifsudestemg.sctapi.api.dto;

import br.edu.ifsudestemg.sctapi.model.entity.Administrador;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
//import org.modelmapper.ModelMapper;
//import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdministradorDTO {
    private Long id;
    private String nome;
    private String cpf;
    private String email;
    private String telefone;
    private boolean admin;

    public static AdministradorDTO create(Administrador administrador) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(administrador, AdministradorDTO.class);


}
