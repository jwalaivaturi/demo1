package com.example;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ssm.SsmClient;
import software.amazon.awssdk.services.ssm.model.GetParameterRequest;
import software.amazon.awssdk.services.ssm.model.GetParameterResponse;
import software.amazon.awssdk.services.ssm.model.PutParameterRequest;
import software.amazon.awssdk.services.ssm.model.SsmException;

/**
 * Hello world!
 */
public final class App {
    private static final String REGISTRAR_TRUSTED_SECRET = "registrar/trustedSecret";
    private static final String REGISTRAR_TRUSTED_KEY = "registrar/trustedKey";
    private App() {
    }

    /**
     * Says hello to the world.
     * @param args The arguments of the program.
     */
    public static void main(String[] args) {
        System.out.println("Start");
        Region region = Region.US_EAST_1;
        SsmClient ssmClient = SsmClient.builder()
            .region(region)
            .build();
        putParams( ssmClient );
        getParams(ssmClient);
        ssmClient.close();
        System.out.println("Done");
    }

    private static void getParams( SsmClient ssmClient )
    {
        try {
            GetParameterRequest parameterRequest = GetParameterRequest.builder()
                .name(REGISTRAR_TRUSTED_KEY)
                .build();

            GetParameterResponse parameterResponse = ssmClient.getParameter(parameterRequest);
            System.out.println("The parameter value is "+parameterResponse.parameter().value());

        } catch (SsmException e) {
        System.err.println(e.getMessage());
        System.exit(1);
        }

    }

    private static void putParams( SsmClient ssmClient )
    {
        try {
            PutParameterRequest keyPutRequest = PutParameterRequest.builder().name(REGISTRAR_TRUSTED_KEY).value("key101").build();
            PutParameterRequest secretPutRequest = PutParameterRequest.builder().name(REGISTRAR_TRUSTED_SECRET).value("Value101").build();
            ssmClient.putParameter(keyPutRequest);
            ssmClient.putParameter(secretPutRequest);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
       
    }
}
