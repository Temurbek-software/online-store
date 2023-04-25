package online.market.service.entity.impl;

import lombok.RequiredArgsConstructor;
import online.market.model.entity.Publisher;
import online.market.model.repository.PublisherRepository;
import online.market.service.entity.PublisherService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PublisherServiceImpl implements PublisherService {
    private final PublisherRepository publisherRepository;


    @Override
    public Page<Publisher> findPaginatedPublisher(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return this.publisherRepository.findAll(pageable);
    }

    @Override
    public List<Publisher> findPublisherList(int year) {
        return publisherRepository.getPublisherByCreatedAt(year);
    }

    @Override
    public Publisher getOnePublisher(long id) {
        Publisher publisherDTO = (publisherRepository.getOne(id));
        return publisherDTO;
    }

    @Override
    public List<Publisher> getAllPublisher(boolean data) {
        List<Publisher> publisherDTOS = publisherRepository.getAllPublisherIfDeleted(data);
        return publisherDTOS;
    }

    @Override
    public void updatePublisherByIdWithNewOne(long id, Publisher publisher) {
        publisherRepository.save(publisher);
    }

    @Override
    public boolean deletePubById(long id) {
        Optional<Publisher> publisher = publisherRepository.findById(id);
        if (publisher.isPresent()) {
            publisherRepository.deleteById(publisher.get().getId());
            return true;
        }
        return false;
    }

    @Override
    public void insertNewPublisher(Publisher publisherDTO) {
        publisherRepository.save(publisherDTO);
    }
}
