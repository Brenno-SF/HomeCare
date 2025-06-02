package com.eng.homecare.mapper;

import com.eng.homecare.entities.Address;
import com.eng.homecare.entities.Patient;
import com.eng.homecare.entities.Phone;
import com.eng.homecare.entities.User;
import com.eng.homecare.enums.TypeUser;
import com.eng.homecare.request.PatientRequestDTO;
import com.eng.homecare.response.AddressResponseDTO;
import com.eng.homecare.response.PatientResponseDTO;
import com.eng.homecare.response.PhoneResponseDTO;
import org.springframework.web.bind.annotation.Mapping;

import java.util.List;

public class PatientMapper {

    public static Patient toEntity(PatientRequestDTO dto) {
        User user = new User();
        user.setName(dto.name());
        user.setEmail(dto.email());
        user.setPassword(dto.password());
        user.setBirthDate(dto.birthDate());
        user.setGender(dto.gender());
        user.setTypeUser(TypeUser.Patient);


        List<Address> addresses = dto.addresses().stream().map(addrDto -> {
            Address address = new Address();
            address.setCity(addrDto.city());
            address.setNeighborhood(addrDto.neighborhood());
            address.setZipCode(addrDto.zipCode());
            address.setComplement(addrDto.complement());
            address.setNumber(addrDto.number());
            address.setStreet(addrDto.street());
            address.setFu(addrDto.fu());
            address.setUser(user);
            return address;
        }).toList();

        user.setAddresses(addresses);

        List<Phone> phones = dto.phones().stream().map(phoneDTO -> {
            Phone phone = new Phone();
            phone.setPhoneNumber(phoneDTO.phoneNumber());
            phone.setTypePhone(phoneDTO.typePhone());
            phone.setUser(user);
            return phone;
        }).toList();

        user.setPhones(phones);

        Patient patient = new Patient();
        patient.setCpf(dto.cpf());
        patient.setUser(user);

        return patient;
    }

    public static PatientResponseDTO toDTO(Patient patient) {
        User user = patient.getUser();
        List<AddressResponseDTO> addressDTOs = user.getAddresses().stream().map(address ->
                new AddressResponseDTO(
                        address.getId(),
                        address.getCity(),
                        address.getNeighborhood(),
                        address.getZipCode(),
                        address.getComplement(),
                        address.getNumber(),
                        address.getStreet(),
                        address.getFu()
                )
        ).toList();

        List<PhoneResponseDTO> phoneDTOs = user.getPhones().stream().map(phone ->
                new PhoneResponseDTO(
                        phone.getId(),
                        phone.getPhoneNumber(),
                        phone.getTypePhone()
                )
        ).toList();

        return new PatientResponseDTO(user.getUserId(), user.getName(), user.getEmail(), patient.getCpf(), user.getBirthDate(), user.getGender(), user.getRegister(),addressDTOs, phoneDTOs);
    }



}
