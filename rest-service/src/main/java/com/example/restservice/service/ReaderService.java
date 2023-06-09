package com.example.restservice.service;

import com.example.restservice.entity.ReaderEntity;
import com.example.restservice.model.ReaderModel;
import com.example.restservice.repository.ReaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReaderService {
    @Autowired
    private ReaderRepository readerRepository;

    public ReaderEntity addReader(ReaderEntity reader) {
        return readerRepository.save(reader);
    }

    public List<ReaderModel> getAll() {
        List<ReaderEntity> entitiesList = (List<ReaderEntity>) readerRepository.findAll();
        return entitiesList
            .stream()
            .map(ReaderModel::toModel)
            .collect(Collectors.toList());
    }

    public ReaderModel getOne(Long id) {
        ReaderEntity reader = readerRepository.findById(id).get();
        return ReaderModel.toModel(reader);
    }

    public Long delete(Long id) {
        readerRepository.findById(id).get();
        readerRepository.deleteById(id);
        return id;
    }

    public ReaderModel update(ReaderEntity updatedReader) {
        ReaderEntity reader = readerRepository.findById(updatedReader.getId()).get();
        reader.setName(updatedReader.getName());
        reader.setSurname(updatedReader.getSurname());
        reader.setSecondName(updatedReader.getSecondName());
        return ReaderModel.toModel(readerRepository.save(reader));
    }
}
