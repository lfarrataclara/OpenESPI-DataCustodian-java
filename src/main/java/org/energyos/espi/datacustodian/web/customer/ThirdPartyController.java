package org.energyos.espi.datacustodian.web.customer;

import org.energyos.espi.datacustodian.service.ThirdPartyService;
import org.energyos.espi.datacustodian.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/RetailCustomer/{retailCustomerId}/ThirdPartyList")
public class ThirdPartyController extends BaseController {

    @Autowired
    private ThirdPartyService thirdPartyService;

    @RequestMapping(method = RequestMethod.GET)
    public String index(ModelMap model) {
        model.put("thirdParties", thirdPartyService.findAll());
        return "/customer/thirdparties/index";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String selectThirdParty(@RequestParam String Third_party_URL, @RequestParam String Third_party) {
        return "redirect:" + Third_party_URL + "?scope=all&DataCustodianID=" + Third_party;
    }

    public void setThirdPartyService(ThirdPartyService thirdPartyService) {
        this.thirdPartyService = thirdPartyService;
    }
}
