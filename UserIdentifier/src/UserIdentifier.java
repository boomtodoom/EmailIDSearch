import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URLConnection;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;
import java.net.URL;

public class UserIdentifier {
    public static void main(String[] args) throws IOException, InterruptedException {
        UserIdentifier idCode = new UserIdentifier(); //instantiates a new object of the class
        Scanner scan = new Scanner(System.in); //instantiates a scanner object to read user input
        System.out.println("Please enter the username of the person you wish to search for: "); //Outputs text asking the user for an input
        String userInput = scan.nextLine(); //takes the user input and assigns it to a variable
        URL completeUrl = idCode.generateUrl(userInput); //creates a url using the generateUrl method
        idCode.scanSite(completeUrl); //scans the website using the scanSite method

    }

    URL generateUrl(String username) throws MalformedURLException {
        URL url = null; //instantiates the variable url
        if(username!=null){
            try {
                url = new URL("https://www.ecs.soton.ac.uk/people/"+username); //if the username is not null then the url is created
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        return url; //the url is returned from the method
    }

    void scanSite(URL url){
        String parseLine; //instantiates a string variable
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream())); //creates a buffered reader that reads every line in the website
            while ((parseLine = br.readLine()) != null) {  //runs through every line in the html contents of the webpage
                if(parseLine.contains("property=\"name\"")){
                    int nameIndex = parseLine.indexOf("property=\"name\""); //if the line contains the required string that index of that string is saved
                    int endIndex = parseLine.indexOf("<em property=\"honorificSuffix\""); //the index of the string succeeding the name is also saved
                    System.out.println(parseLine.substring(nameIndex+16,endIndex)); //the two index variables are used to extract the name from the html and print it
                }
            }
            br.close(); //the buffered reader is closed

        } catch (MalformedURLException mUE) {
            System.out.println(mUE);

        } catch (IOException iOE) {
            System.out.println(iOE);
        }
    }

}
