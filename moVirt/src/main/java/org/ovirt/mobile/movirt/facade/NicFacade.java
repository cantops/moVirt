package org.ovirt.mobile.movirt.facade;

import org.androidannotations.annotations.EBean;
import org.ovirt.mobile.movirt.facade.predicates.VmIdPredicate;
import org.ovirt.mobile.movirt.model.Nic;
import org.ovirt.mobile.movirt.rest.CompositeResponse;
import org.ovirt.mobile.movirt.rest.Request;
import org.ovirt.mobile.movirt.rest.Response;

import java.util.List;

import static org.ovirt.mobile.movirt.util.ObjectUtils.requireSignature;

@EBean
public class NicFacade extends BaseEntityFacade<Nic> {

    public NicFacade() {
        super(Nic.class);
    }

    @Override
    protected Request<List<Nic>> getSyncAllRestRequest(String... ids) {
        requireSignature(ids, "vmId");
        String vmId = ids[0];
        return oVirtClient.getNicsRequest(vmId);
    }

    @Override
    protected CompositeResponse<List<Nic>> getSyncAllResponse(final Response<List<Nic>> response, final String... ids) {
        requireSignature(ids, "vmId");
        String vmId = ids[0];

        return respond()
                .withScopePredicate(new VmIdPredicate<>(vmId))
                .asUpdateEntitiesResponse()
                .addResponse(response);
    }
}
