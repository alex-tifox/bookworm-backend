package pl.bookworm.bookworm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.bookworm.bookworm.repository.RepositoryName;

@Service
public class ServiceName {

    private RepositoryName repositoryName;

    @Autowired
    public ServiceName(RepositoryName repositoryName) {
        this.repositoryName = repositoryName;
    }
}
