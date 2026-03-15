package com.wms.core.application.service;

import com.wms.core.domain.warehouse.LocationType;
import com.wms.core.infrastructure.persistence.LocationTypeRepository;
import com.wms.core.infrastructure.web.dto.request.CreateLocationTypeRequest;
import com.wms.core.infrastructure.web.dto.response.LocationTypeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LocationTypeService {

    private final LocationTypeRepository locationTypeRepository;

    public LocationType createLocationType(CreateLocationTypeRequest request) {

        String name = request.getName().trim().toUpperCase();
        String indicator = request.getIndicator().trim().toUpperCase();

        locationTypeRepository.findByName(name)
                .ifPresent(type -> {
                    throw new IllegalArgumentException("Location type name already exists");
                });

        locationTypeRepository.findByIndicator(indicator)
                .ifPresent(type -> {
                    throw new IllegalArgumentException("Location type indicator already exists");
                });

        LocationType locationType = new LocationType(
                UUID.randomUUID(),
                name,
                indicator,
                true,
                null
        );

        return locationTypeRepository.save(locationType);
    }

    public List<LocationType> createBulk(List<CreateLocationTypeRequest> requests) {

        // Obtener nombres del requet
        List<String> incomingNames = requests.stream()
                .map(req -> req.getName().trim().toUpperCase())
                .toList();

        //Busca los que existen en la base de datos
        List<LocationType> existingTypes = locationTypeRepository.findByNameIn(incomingNames);

        Set<String> existingNames = existingTypes.stream()
                .map(type -> type.getName().toUpperCase())
                .collect(Collectors.toSet());

        //Evita duplicados dentro del request
        Set<String> processed = new HashSet<>();

        List<LocationType> typesToSave = new ArrayList<>();

        for(CreateLocationTypeRequest req : requests){

            String name = req.getName().trim().toUpperCase();
            String indicator = req.getIndicator().trim().toUpperCase();

            if (existingNames.contains(name)){
                continue;
            }

            if (processed.contains(name)){
                continue;
            }

            processed.add(name);
            typesToSave.add(
                    new LocationType(
                            UUID.randomUUID(),
                            name,
                            indicator,
                            true,
                            null
                    )
            );
        }
        return locationTypeRepository.saveAll(typesToSave);
    }

    public List<LocationTypeResponse> getAll() {

        return locationTypeRepository.findByActiveTrue()
                .stream()
                .map(type -> new LocationTypeResponse(
                        type.getTypeId(),
                        type.getName(),
                        type.getIndicator()
                ))
                .toList();
    }

}
