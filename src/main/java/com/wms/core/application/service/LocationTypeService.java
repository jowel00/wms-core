package com.wms.core.application.service;

import com.wms.core.application.mapper.LocationTypeMapper;
import com.wms.core.domain.exception.ResourceConflictException;
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
    private final LocationTypeMapper locationTypeMapper;

    public LocationTypeResponse createLocationType(CreateLocationTypeRequest request) {

        String name = request.getName().trim().toUpperCase();
        String indicator = request.getIndicator().trim().toUpperCase();

        Map<String, String> conflicts = new HashMap<>();

        if (locationTypeRepository.existsByName(name)) {
            conflicts.put("name", "El name [" + name + "] ya existe ");
        }

        if (locationTypeRepository.existsByIndicator(indicator)) {
            conflicts.put("indicator", "El indicator [" + indicator + "] ya está en uso");
        }

        if (!conflicts.isEmpty()){
            throw new ResourceConflictException("LocationType", conflicts);
        }

        LocationType locationType = locationTypeMapper.toDomain(request);
        return locationTypeMapper.toResponse(locationTypeRepository.save(locationType));
    }

    public List<LocationTypeResponse> createBulk(List<CreateLocationTypeRequest> requests) {

        // Obtener nombres del requet
        List<String> incomingNames = requests.stream()
                .map(req -> req.getName().trim().toUpperCase())
                .toList();
        // Obtener indicadores del requet
        List<String> incomingIndicators = requests.stream()
                .map(req -> req.getIndicator().trim().toUpperCase())
                .toList();

        //Busca los que existen en la base de datos
        Set<String> existingNames = locationTypeRepository.findByNameIn(incomingNames)
                .stream()
                .map(type -> type.getName().toUpperCase())
                .collect(Collectors.toSet());

        Set<String> existingIndicators = locationTypeRepository.findByIndicatorIn(incomingIndicators)
                .stream()
                .map(type -> type.getIndicator().toUpperCase())
                .collect(Collectors.toSet());

        //Evita duplicados dentro del request
        Set<String> processedNames = new HashSet<>();
        Set<String> processedIndicators = new HashSet<>();
        List<LocationType> typesToSave = new ArrayList<>();


        for(CreateLocationTypeRequest req : requests){
            String name = req.getName().trim().toUpperCase();
            String indicator = req.getIndicator().trim().toUpperCase();

            if (existingNames.contains(name) || processedNames.contains(name)){
                continue;
            }

            if (existingIndicators.contains(indicator) || processedIndicators.contains(indicator)){
                continue;
            }

            processedNames.add(name);
            processedIndicators.add(indicator);
            typesToSave.add(locationTypeMapper.toDomain(req));
        }
        return locationTypeMapper.toResponseList(locationTypeRepository.saveAll(typesToSave));
    }

    public List<LocationTypeResponse> getAllLocationTypes() {

        return locationTypeMapper.toResponseList(
                locationTypeRepository.findByActiveTrue()
        );

    }

}
