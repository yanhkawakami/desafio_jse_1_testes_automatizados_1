package com.devsuperior.bds02.services;

import com.devsuperior.bds02.dto.EventDTO;
import com.devsuperior.bds02.entities.City;
import com.devsuperior.bds02.entities.Event;
import com.devsuperior.bds02.repositories.CityRepository;
import com.devsuperior.bds02.repositories.EventRepository;
import com.devsuperior.bds02.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class EventService {

    @Autowired
    EventRepository repository;

    @Autowired
    CityRepository cityRepository;

    @Transactional
    public EventDTO update(Long id, EventDTO dto) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Evento não econtrado com ID : " + id);
        }
        Event event = repository.getReferenceById(id);
        copyDtoToEntity(event, dto);
        Event result = repository.save(event);
        return new EventDTO(result);
    }

    public void copyDtoToEntity (Event entity, EventDTO dto) {
        entity.setName(dto.getName());
        entity.setDate(dto.getDate());
        entity.setUrl(dto.getUrl());
        City city = cityRepository.getReferenceById(dto.getCityId());
        entity.setCity(city);
    }

}
