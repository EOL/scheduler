package org.bibalex.eol.scheduler.resource;

import org.bibalex.eol.scheduler.content_partner.ContentPartner;
import org.bibalex.eol.scheduler.content_partner.ContentPartnerRepository;
import org.bibalex.eol.scheduler.content_partner.ContentPartnerService;
import org.bibalex.eol.scheduler.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by sara.mustafa on 4/18/17.
 */
@Service
public class ResourceService {

    @Autowired
    private ResourceRepository resourceRepository;
    @Autowired
    private ContentPartnerService contentPartnerService;

    public long createResource(long contentPartnerId, Resource resource){
        contentPartnerService.validateContentPartner(contentPartnerId);
        resource.setContentPartner(new ContentPartner(contentPartnerId));
        return resourceRepository.save(resource).getId();
    }

    public Resource updateResource(long contentPartnerId, long resourceId ,Resource resource){
        contentPartnerService.validateContentPartner(contentPartnerId);
        validateResource(resourceId);
        resource.setContentPartner(new ContentPartner(contentPartnerId));
        resource.setId(resourceId);
         return resourceRepository.save(resource);
    }

    public List<Resource> getResources(long contentPartnerId){
        contentPartnerService.validateContentPartner(contentPartnerId);
        return resourceRepository.findByContentPartnerId(contentPartnerId);
    }
    public void validateResource(long resourceId){
        resourceRepository.findById(resourceId).orElseThrow(() -> new NotFoundException("resource", resourceId));
    }
}
