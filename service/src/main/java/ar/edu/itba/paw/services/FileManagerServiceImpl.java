package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.services.FileManagerService;
import ar.edu.itba.paw.interfaces.util.Validation;
import com.sun.jndi.toolkit.url.Uri;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartRequest;

import java.security.Policy;
import java.security.Security;

@Service
public class FileManagerServiceImpl implements FileManagerService {
    private static final String API_KEY = "ALHqPrmXSCmPUvjIctSPwz";
    private static final String APP_SECRET = "";

    public void pictureUpload() {
        String picture = Capture.capturePhoto(1024, -1);
        if(picture!=null){
            String filestack = "https://www.filestackapi.com/api/store/S3?key=MY_KEY&filename=myPicture.jpg";
            Request request = new MultipartRequest() {
                protected void readResponse(InputStream input) throws IOException  {
                    JSONParser jp = new JSONParser();
                    Map<String, Object> result = jp.parseJSON(new InputStreamReader(input, "UTF-8"));
                    String url = (String)result.get("url");
                }
            };
            request.setUrl(filestack);
            try {
                request.addData("fileUpload", picture, "image/jpeg");
                request.setFilename("fileUpload", "myPicture.jpg");
                NetworkManager.getInstance().addToQueue(request);
            } catch(IOException err) {
                err.printStackTrace();
            }
        }
    }

}
