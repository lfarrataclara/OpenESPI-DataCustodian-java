package org.energyos.espi.datacustodian.web.custodian;

import org.energyos.espi.datacustodian.domain.RetailCustomer;
import org.energyos.espi.datacustodian.domain.ServiceCategory;
import org.energyos.espi.datacustodian.domain.UsagePoint;
import org.energyos.espi.datacustodian.service.RetailCustomerService;
import org.energyos.espi.datacustodian.service.UsagePointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.*;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.UUID;

@Controller
@RequestMapping("/custodian/retailcustomers")
@PreAuthorize("hasRole('ROLE_CUSTODIAN')")
public class AssociateUsagePointController {

    @Autowired
    private RetailCustomerService retailCustomerService;

    public void setService(UsagePointService service) {
        this.service = service;
    }

    @Autowired
    UsagePointService service;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(new UsagePointFormValidator());
    }

    @RequestMapping(value = "/{retailCustomerId}/usagepoints/form", method = RequestMethod.GET)
    public String form(@PathVariable Long retailCustomerId, ModelMap model) {
        model.put("usagePointForm", new UsagePointForm());
        model.put("retailCustomerId", retailCustomerId);

        return "/custodian/retailcustomers/usagepoints/form";
    }

    @RequestMapping(value = "/{retailCustomerId}/usagepoints/create", method = RequestMethod.POST)
    public String create(@PathVariable Long retailCustomerId, @ModelAttribute("usagePointForm") @Valid UsagePointForm usagePointForm, BindingResult result) {
        if (result.hasErrors())
            return "/custodian/retailcustomers/usagepoints/form";
        try {
            UsagePoint usagePoint = new UsagePoint();
            usagePoint.setUUID(UUID.fromString(usagePointForm.getUUID()));
            usagePoint.setDescription(usagePointForm.getDescription());

            RetailCustomer retailCustomer = retailCustomerService.findById(retailCustomerId);
            usagePoint.setServiceCategory(new ServiceCategory(ServiceCategory.ELECTRICITY_SERVICE));
            usagePoint.setRetailCustomer(retailCustomer);
            service.createOrReplaceByUUID(usagePoint);
        } catch (ConstraintViolationException x) {
            result.addError(new ObjectError("usagePoint", "Unable to save the usage point"));
            return "/custodian/retailcustomers/usagepoints/form";
        }
        return "redirect:/custodian/retailcustomers";
    }

    public void setRetailCustomerService(RetailCustomerService retailCustomerService) {
        this.retailCustomerService = retailCustomerService;
    }

    public static class UsagePointForm {
        private String uuid;
        private String description;

        public String getUUID() {
            return uuid;
        }

        public void setUUID(String uuid) {
            this.uuid = uuid;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }

    public static class UsagePointFormValidator implements Validator {

        public boolean supports(Class clazz) {
            return UsagePointForm.class.isAssignableFrom(clazz);
        }

        public void validate(Object target, Errors errors) {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "UUID", "field.required", "UUID is required");
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "field.required", "Description is required");
            try {
                UsagePointForm form = (UsagePointForm)target;
                UUID.fromString(form.getUUID());
            } catch(IllegalArgumentException x) {
                errors.rejectValue("UUID", "uuid.required", null, "Must be a valid UUID Ex. 550e8400-e29b-41d4-a716-446655440000");
            }
        }
    }
}
