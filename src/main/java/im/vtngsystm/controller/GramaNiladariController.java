package im.vtngsystm.controller;

import im.vtngsystm.dto.GramaNiladariDTO;
import im.vtngsystm.dto.VoterDTO;
import im.vtngsystm.dto.Voter_GRN_DTO;
import im.vtngsystm.service.custom.GramaNiladariService;
import im.vtngsystm.service.custom.VoterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/grama-niladari")
public class GramaNiladariController {

    @Autowired
    VoterService voterService;

    @Autowired
    GramaNiladariService gramaNiladariService;

    @PostMapping("/grn/voters/add")
    public boolean saveVoter(@RequestBody Voter_GRN_DTO voter_grn_dto) {
        voterService.save(voter_grn_dto);
        System.out.println("voter saved correctly");
        return true;
    }

    @PostMapping("/grn/voters/{id}")
    public boolean updateVoterDetails(@PathVariable("id") String voterId,
                                      @RequestBody VoterDTO voterDTO) {
        voterService.update(voterId, voterDTO);
        System.out.println("voter updated correctly");
        return true;
    }

    @DeleteMapping("/grn/voters/{id}")
    public boolean deleteVoter(@PathVariable("id") String voterId) {
        voterService.delete(voterId);
        return true;
    }

    @GetMapping("/grn/voters/{id}")
    public VoterDTO findVoter(@PathVariable("id") String voterId) {
        VoterDTO byID = voterService.findByID(voterId);
        byID.toString();
        return byID;
    }

    @PostMapping(value = "/grn/voters", consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<VoterDTO> findGramaNiladarisVoters(@RequestBody GramaNiladariDTO gramaNiladariDTO) {
        List<VoterDTO> votersByGramaNiladari = voterService.findVotersByGramaNiladari(gramaNiladariDTO);
        votersByGramaNiladari.toString();
        return votersByGramaNiladari;
    }

//    @PostMapping("/grn/{id}")
//    public boolean updateMyDetails(@PathVariable("id") String id, GramaNiladariDTO gramaNiladariDTO) {
//        gramaNiladariService.update(id, gramaNiladariDTO);
//        return true;
//    }

}
