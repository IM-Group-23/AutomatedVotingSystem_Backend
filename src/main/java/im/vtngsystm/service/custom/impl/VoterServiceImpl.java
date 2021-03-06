package im.vtngsystm.service.custom.impl;

import im.vtngsystm.dto.GramaNiladariDTO;
import im.vtngsystm.dto.VoterDTO;
import im.vtngsystm.dto.Voter_GRN_DTO;
import im.vtngsystm.entity.GramaNiladari;
import im.vtngsystm.entity.PollingDivision;
import im.vtngsystm.entity.Vote;
import im.vtngsystm.entity.Voter;
import im.vtngsystm.repository.GramaNiladariRepository;
import im.vtngsystm.repository.PollingDivisionRepository;
import im.vtngsystm.repository.VoteRepository;
import im.vtngsystm.repository.VoterRepository;
import im.vtngsystm.service.custom.VoterService;
import im.vtngsystm.service.util.EntityDtoConvertor;
import im.vtngsystm.service.util.SMS_Sender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class VoterServiceImpl implements VoterService {

    @Autowired
    EntityDtoConvertor entityDtoConvertor;
    @Autowired
    private VoterRepository voterRepository;
    @Autowired
    private PollingDivisionRepository pollingDivisionRepository;
    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private GramaNiladariRepository gramaNiladariRepository;

    @Autowired
    SMS_Sender sms_sender;

    @Override
    public void save(Voter_GRN_DTO voter_grn_dto) {
        Voter entity = (Voter) entityDtoConvertor.convertToEntity(voter_grn_dto.getVoterDTO());
        GramaNiladari gramaNiladari = gramaNiladariRepository.findById(voter_grn_dto.getGramaNiladariDTO().getUsername()).get();
        entity.setGramaNiladari(gramaNiladari);
        entity.setPollingDivision(gramaNiladari.getPollingDivision());
        voterRepository.save(entity);
    }

    @Override
    public void update(String id, VoterDTO dto) {
        if (dto.getUsername().equals(id)) {
            throw new RuntimeException("Voter's ID mismatched");
        }
        if (voterRepository.existsById(dto.getUsername())) {
            Voter entity = (Voter) entityDtoConvertor.convertToEntity(dto);
            voterRepository.save(entity);
        } else {
            throw new RuntimeException("Such Voter doesn't exist");
        }
    }

    @Override
    public void delete(String id) {
        voterRepository.deleteById(id);
    }

    @Override
    public VoterDTO findByID(String id) {
        Voter voter = voterRepository.findById(id).get();
        return (VoterDTO) entityDtoConvertor.convertToDTO(voter);
    }

    @Override
    public VoterDTO findByName(String name) {
        Voter voter = voterRepository.findById(name).get();
        return (VoterDTO) entityDtoConvertor.convertToDTO(voter);
    }

    @Override
    public List<VoterDTO> findAll() {
        List<Voter> all = voterRepository.findAll();
        return entityDtoConvertor.convertToDtoList(all);
    }


    @Override
    public List<VoterDTO> findVoterByNameInPollingDivision(String name, int pollId) {
        PollingDivision pollingDivision = pollingDivisionRepository.findById(pollId).get();
        List<Voter> voters = voterRepository.findVotersByNameIsLikeAndPollingDivision(name, pollingDivision);
        return entityDtoConvertor.convertToDtoList(voters);
    }

    @Override
    public List<Voter> findVotersInPollingDivision(int pollId) {
        PollingDivision pollingDivision = pollingDivisionRepository.findById(pollId).get();
        List<Voter> voters = voterRepository.findVotersByPollingDivision(pollingDivision);
        return entityDtoConvertor.convertToDtoList(voters);
    }

    @Override
    public List<VoterDTO> findVotersByGramaNiladari(GramaNiladariDTO gramaNiladariDTO) {
        GramaNiladari gramaNiladari = gramaNiladariRepository.findById(gramaNiladariDTO.getUsername()).get();
        List<Voter> votersByGramaNiladari = voterRepository.findVotersByGramaNiladari(gramaNiladari);
        List<VoterDTO> list = entityDtoConvertor.convertToDtoList(votersByGramaNiladari);
        return list;
    }

    @Override
    public long getCount() {
        return voterRepository.count();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public int login(String nic) {
        Voter voterByUsername = voterRepository.findById(nic).get();
        if (voterByUsername != null) {
            System.out.println("voter is identified in database-----------------------");
            List<Vote> votesByVoter = voteRepository.findVotesByVoter(nic);
            System.out.println(votesByVoter + "-----------------------------------------------");
            if (votesByVoter == null || votesByVoter.size() == 0) {
                System.out.println("voter has not already voted");
                boolean b = sms_sender.generateOTP(voterByUsername);
                if (b) {
                    return 1;
                } else
//                    return VoterStatus.otp_sent.toString();
//                return VoterStatus.error_logging_in.toString();
                    return 0;
            } else {
                System.out.println("voter has already voted");
//                return VoterStatus.already_voted.toString();
                return 2;
            }
        }
//        return VoterStatus.invalid_nic.toString();
        return -1;
    }

    @Override
    public boolean checkOTP(String nic, String otp) {
        System.out.println(nic + "-------------------------");
        Voter voter = voterRepository.findById(nic).get();
        if (voter.getPassword().equals(otp))
            return true;
        else
            return false;
    }
}
