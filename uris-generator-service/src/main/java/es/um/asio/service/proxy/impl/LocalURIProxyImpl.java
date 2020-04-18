package es.um.asio.service.proxy.impl;

import com.izertis.abstractions.exception.NoSuchEntityException;
import es.um.asio.service.filter.CanonicalURIFilter;
import es.um.asio.service.filter.LocalURIFilter;
import es.um.asio.service.model.CanonicalURI;
import es.um.asio.service.model.LocalURI;
import es.um.asio.service.model.User;
import es.um.asio.service.proxy.CanonicalURIProxy;
import es.um.asio.service.proxy.LocalURIProxy;
import es.um.asio.service.service.CanonicalURIService;
import es.um.asio.service.service.LocalURIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Proxy service implementation for {@link User}. Performs DTO conversion and permission checks.
 */
@Service
public class LocalURIProxyImpl implements LocalURIProxy {



    /**
     * Service layer.
     */
    @Autowired
    private LocalURIService service;

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<LocalURI> find(final String identifier) {
        return this.service.find(identifier);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<LocalURI> findPaginated(final LocalURIFilter filter, final Pageable pageable) {
        return this.service.findPaginated(filter,pageable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<LocalURI> findAll() {
        return this.service.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LocalURI save(final LocalURI entity) {
        List<LocalURI> filtered = this.service.getAllByLocalURI(entity);
        if (filtered.size() == 0) {
            return this.service.save(entity);
        } else {
            for (LocalURI e :filtered) {
                e.merge(entity);
                try {
                    return this.service.update(e);
                } catch (NoSuchEntityException ex) {
                    ex.printStackTrace();
                }
            }
            return null;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<LocalURI> save(final Iterable<LocalURI> entities) {
        return this.service.save(entities);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LocalURI update(final LocalURI entity) throws NoSuchEntityException {
        return this.service.update(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(final LocalURI entity) {
        this.service.delete(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(final String identifier) {
        this.service.delete(identifier);
    }

    @Override
    public List<LocalURI> getAllByLocalURI(LocalURI localURI) {
        return this.service.getAllByLocalURI(localURI);
    }

    @Override
    public LocalURI getAllByLocalURIStr(String localURI) {
        return this.service.getAllByLocalURIStr(localURI);
    }

    @Override
    public LocalURI getAllByCanonicalURILanguageStrAndStorageTypeStr(String canonicalURILanguage, String storageTypeStr) {
        return this.service.getAllByCanonicalURILanguageStrAndStorageTypeStr(canonicalURILanguage,storageTypeStr);
    }
}
