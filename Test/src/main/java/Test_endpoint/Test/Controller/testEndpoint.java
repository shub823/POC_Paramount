package Test_endpoint.Test.Controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import Test_endpoint.Test.Model.Model;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

@RestController
public class testEndpoint {
	
	

	@Autowired
	RestTemplate restTemplate;
	
	@RequestMapping(value="/test", method=RequestMethod.GET)
	public String downloadImage(@RequestParam("id") String id) throws Exception{
		
		File file = new File("D:\\file1.psd");
		File target = new File("D:\\Test2\\file3.psd");
		try (
				InputStream inputStream = new FileInputStream(file);
	            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
				OutputStream outputStream = new FileOutputStream(target);
	    ) {
	        byte[] buffer = new byte[4 * 1024];
	        int read;
	        while ((read = bufferedInputStream.read(buffer, 0, buffer.length)) != -1) {
	            outputStream.write(buffer, 0, read);
	        }
	    }
		return "Done"; 
		
}
	
	
	@RequestMapping(value="/test2", method=RequestMethod.GET)
	public String downloadImage2() throws Exception{
		
		String URL ="https://enterpriseuat.viacomcbs.com/customrestservice/paramountuwf/largeFileDownload/f3ea99b1f9bd639be959f8cbe0d8581c3c701886";
		HttpHeaders headers = new HttpHeaders();
		HttpEntity <String> entity = new HttpEntity<String>(headers);
		ResponseEntity<Resource> respEntity = restTemplate.exchange(URL, HttpMethod.GET,entity,Resource.class);
		File target = new File("D:\\Test2\\"+respEntity.getHeaders().getContentDisposition().getFilename());
		try (
				InputStream inputStream = respEntity.getBody().getInputStream();
	            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
				OutputStream outputStream = new FileOutputStream(target);
	    ) {
	        byte[] buffer = new byte[8 * 1024];
	        int read;
	        System.out.println("Downloading....");
	        while ((read = bufferedInputStream.read(buffer, 0, buffer.length)) != -1) {
	            outputStream.write(buffer, 0, read);
	       }
	        return "Done";
	    }
		
		
   }
	
	@RequestMapping(value="/test3", method=RequestMethod.GET)
	public void downloadImage3() throws Exception{
		
		URL myURL = new URL("https://enterpriseuat.viacomcbs.com/customrestservice/paramountuwf/largeFileDownload/f3ea99b1f9bd639be959f8cbe0d8581c3c701886");
		myURL.openConnection();
		File target = new File("D:\\Test2\\file6.psd" );
		try {
			System.out.println("Downloading....");
			long start = System.currentTimeMillis();
			FileUtils.copyURLToFile(myURL, target);
            long end = System.currentTimeMillis();
            System.out.println("Completed....in ms : " + (end - start));
	        }
		 catch (Exception e) {
             e.printStackTrace();
         }
		
	}
	
	@RequestMapping(value="/test4", method=RequestMethod.GET)
	public void downloadImage4() throws Exception{
		RequestCallback requestCallback = request -> request.getHeaders()
		        .setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM, MediaType.ALL));
		ResponseExtractor<Void> responseExtractor = response -> {
		    // Here I write the response to a file but do what you like
		    Path path = Paths.get("D:\\Test2\\file8.psd");
		    Files.copy(response.getBody(), path);
		    return null;
		};
		restTemplate.execute(URI.create("https://enterpriseuat.viacomcbs.com/customrestservice/paramountuwf/largeFileDownload/f3ea99b1f9bd639be959f8cbe0d8581c3c701886"), HttpMethod.GET, requestCallback, responseExtractor);
		//restTemplate.exchange(URL, HttpMethod.GET,entity,responseExtractor.getClass());
   }
	
	
	

	 @RequestMapping(value="/test5", method=RequestMethod.GET)
	public String downloadImage5() throws Exception{
		
		String URL ="https://enterpriseuat.viacomcbs.com/customrestservice/paramountuwf/largeFileDownload/f3ea99b1f9bd639be959f8cbe0d8581c3c701886";
		HttpHeaders headers = new HttpHeaders();
		HttpEntity <String> entity = new HttpEntity<String>(headers);
		ResponseEntity<Resource> respEntity = restTemplate.exchange(URL, HttpMethod.GET,entity,Resource.class);
		InputStream responseInputStream;
		try {
		    responseInputStream = respEntity.getBody().getInputStream();
		    BufferedInputStream bufferedInputStream = new BufferedInputStream(responseInputStream);
		    File file = new File("D:\\Test2\\"+respEntity.getHeaders().getContentDisposition().getFilename());
		    OutputStream os = new FileOutputStream(file);
		    byte[] buffer = new byte[4 * 1024];
	        int read;
	        while ((read = bufferedInputStream.read(buffer, 0, buffer.length)) != -1) {
	            os.write(buffer, 0, read);
	        }
		    
		}
		catch (IOException e) {
		    throw new RuntimeException(e);
		}
		return "Done";
	
}
	 
	
	@RequestMapping(value="/test6", method=RequestMethod.GET)
	public ResponseEntity<byte[]> downloadImage6() throws Exception{
		
		String URL ="https://enterpriseuat.viacomcbs.com/customrestservice/paramountuwf/largeFileDownload/f3ea99b1f9bd639be959f8cbe0d8581c3c701886";
		HttpHeaders headers = new HttpHeaders();
		HttpEntity <String> entity = new HttpEntity<String>(headers);
		ResponseEntity<byte[]> respEntity = restTemplate.exchange(URL, HttpMethod.GET,entity,byte[].class);
		File file = new File("D:\\Test2\\"+respEntity.getHeaders().getContentDisposition().getFilename());
		OutputStream os = new FileOutputStream(file);
		os.write(respEntity.getBody());
		
		return  respEntity;
		}
	
	@RequestMapping(value="/test7", method=RequestMethod.GET)
	public void downloadImage7() throws Exception{
		
		String URL ="https://enterpriseuat.viacomcbs.com/customrestservice/paramountuwf/largeFileDownload/f3ea99b1f9bd639be959f8cbe0d8581c3c701886";
		HttpHeaders headers = new HttpHeaders();
		HttpEntity <String> entity = new HttpEntity<String>(headers);
		ResponseEntity<Resource> respEntity = restTemplate.exchange(URL, HttpMethod.GET,entity,Resource.class);
		InputStream responseInputStream;
		try {
		    responseInputStream = respEntity.getBody().getInputStream();
		    File file = new File("D:\\Test2\\"+respEntity.getHeaders().getContentDisposition().getFilename());
		    OutputStream os = new FileOutputStream(file);
		    IOUtils.copy(responseInputStream, os);
		    
		}
		catch (IOException e) {
		    throw new RuntimeException(e);
		}
		
		}
	
	
	@RequestMapping("/test8")
	public Object downloadFile8() throws IOException {
		String URL ="https://enterpriseuat.viacomcbs.com/customrestservice/paramountuwf/largeFileDownload/e77fb4d411a171fde2eac3b9106ce548f361b494";
		// Optional Accept header
		RequestCallback requestCallback = request -> request.getHeaders()
		        .setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM, MediaType.ALL));
		
	    // Streams the response instead of loading it all in memory
	    ResponseExtractor<Void> responseExtractor = response -> {
	        // Here you can write the inputstream to a file or any other place
	        Path path = Paths.get("D:\\Test2\\TulipFever_GEN_.png");
	        Files.copy(response.getBody(), path);
	        return null;
	    };
	    return restTemplate.execute(URL, HttpMethod.GET, requestCallback, responseExtractor);
	}
	
	@RequestMapping("/test9")
	public String imageDownloadApi2(HttpServletRequest req, HttpServletResponse res) throws Exception {
		try {
			InputStream inputStream = new FileInputStream(new File("D:\\JSON.txt"));
			TypeReference<Model> tyepReference = new TypeReference<Model>() {
			};
			ObjectMapper mapper = new ObjectMapper();
			Model model = mapper.readValue(inputStream, tyepReference);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set("x-api-key", "VemHcwHTCcoScRs2yI1OQoVTx5T6Ae46CaysdjjzmA4B70z-x5-cbLFrv5fxCO7m3duwBr7qsmiHYDpCck2v2A==");
			HttpEntity<?> requestEntity = new HttpEntity<Object>(model,headers);
		 	UserCenterRequestCallBack userCenterRequestCallBack = new UserCenterRequestCallBack(restTemplate, requestEntity);
			RestTemplate restTemplate = new RestTemplate();
			String url = "https://enterpriseuat.viacomcbs.com/customrestservice/paramountuwf/original/cleanimage";
			ResponseExtractor<ResponseEntity<InputStream>> responseExtractor = response -> {
				String contentDisposition = response.getHeaders().getFirst("Content-Disposition");
                if (contentDisposition != null) {
                    String filePath = "D:\\" + contentDisposition.split("=")[1];
                    Path path = Paths.get(filePath);
                    Files.copy(response.getBody(), path, StandardCopyOption.REPLACE_EXISTING);
                }
                return null;
            };
            restTemplate.execute(url, HttpMethod.POST, userCenterRequestCallBack, responseExtractor);
        } catch (Exception e) {
            throw e;
        }
		
		return "Image Download API 2 completed";
	}


}
