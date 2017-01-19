
import java.util.ArrayList;
import java.util.Arrays;
import org.hyperledger.fabric.sdk.Chain;
import org.hyperledger.fabric.sdk.ChaincodeLanguage;
import org.hyperledger.fabric.sdk.DeployRequest;
import org.hyperledger.fabric.sdk.FileKeyValStore;
import org.hyperledger.fabric.sdk.Member;
import org.hyperledger.fabric.sdk.QueryRequest;
import org.hyperledger.fabric.sdk.RegistrationRequest;
import org.hyperledger.fabric.sdk.exception.EnrollmentException;
import org.hyperledger.fabric.sdk.exception.RegistrationException;
import org.hyperledger.fabric.sdk.transaction.TransactionContext;

public class test
{

    public static String endpointUrl = "http://172.17.0.3:7050";

    static Chain testChain;

    public static void main(String[] args) throws Exception
    {
        testChain = new Chain("chain1");

        testChain.setMemberServicesUrl("grpc://172.17.0.2:7054", null);

        testChain.setKeyValStore(new FileKeyValStore(System.getProperty("user.home") + "/test.properties"));

        testChain.addPeer("grpc://172.17.0.3:7051", null);
        testChain.eventHubConnect("grpc://172.17.0.3:7053", null);
        //testChain.setDevMode(true);
        Member registrar = testChain.getMember("admin");
        if (!registrar.isEnrolled())
        {
            registrar = testChain.enroll("admin", "Xurw3yU9zI0l");
        }
        testChain.setRegistrar(registrar);
        testChain.setDevMode(true);
        DeployRequest request = new DeployRequest();
        final String chaincodePath = "github.com/hyperledger/chaincode_example02";
        request.setChaincodePath(chaincodePath);
        request.setArgs(new ArrayList<>(Arrays.asList("init", "a", "700", "b", "20000")));
        Member member = getMember("fray", "bank_a");
        request.setChaincodeName("mycc");
        request.setChaincodeLanguage(ChaincodeLanguage.GO_LANG);
//        request.setConfidential(true);
        QueryRequest queryRequest = new QueryRequest();
        queryRequest.setChaincodeName("mycc");
        queryRequest.setArgs(new ArrayList<>(Arrays.asList("query", "a")));
        queryRequest.setChaincodeID(member.deploy(request).getChainCodeID());
        System.err.println(member.query(queryRequest).getMessage());
    }

    private static Member getMember(String enrollmentId, String affiliation) throws RegistrationException, EnrollmentException
    {
        Member member = testChain.getMember(enrollmentId);
        if (!member.isRegistered())
        {
            RegistrationRequest registrationRequest = new RegistrationRequest();
            registrationRequest.setEnrollmentID(enrollmentId);
            registrationRequest.setAffiliation(affiliation);
            //registrationRequest.setAccount(); TODO setAccount missing from registrationRequest?
            member = testChain.registerAndEnroll(registrationRequest);
        } else if (!member.isEnrolled())
        {
            member = testChain.enroll(enrollmentId, member.getEnrollmentSecret());
        }
        return member;
    }
}
