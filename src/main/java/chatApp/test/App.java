package chatApp.test;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.translate.AmazonTranslate;
import com.amazonaws.services.translate.AmazonTranslateClient;
import com.amazonaws.services.translate.model.TranslateTextRequest;
import com.amazonaws.services.translate.model.TranslateTextResult;

public class App {
    private static final String REGION = "us-west-2";

    public static Text translateText(Text text, Context context) {
        AWSCredentialsProvider awsCreds = DefaultAWSCredentialsProviderChain.getInstance();

        System.out.println("Context : " + context.toString());
        System.out.println(text);
        System.out.println("Text: " + text.getMessage());


        AmazonTranslate translate = AmazonTranslateClient.builder()
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds.getCredentials()))
                .withRegion(REGION)
                .build();

        TranslateTextRequest request = new TranslateTextRequest()
                .withText(text.getMessage())
                .withSourceLanguageCode(text.getSource())
                .withTargetLanguageCode(text.getDestination());
        TranslateTextResult result  = translate.translateText(request);
        System.out.println(result.getTranslatedText());

        Text returnText = new Text(result.getTranslatedText(), result.getSourceLanguageCode(), result.getTargetLanguageCode());

        return returnText;
    }



}