/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package base_connectivity;

/**
 *
 * @author RX
 */
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
public class HttpRequestPoster
{
/**
* Reads data from the data reader and posts it to a server via POST request.
* data - The data you want to send
* endpoint - The server's address
* output - writes the server's response to output
* @throws Exception
*/
private static Reader reader;
private static Writer writer;
private static String rq;
private static String ans;
public String postData(String data, URL endpoint) throws Exception
{
    rq = data;
    HttpURLConnection urlc = null;
    try
    {
        urlc = (HttpURLConnection) endpoint.openConnection();
        try
        {
          urlc.setRequestMethod("POST");
        } catch (ProtocolException e)
        {
             throw new Exception("Shouldn't happen: HttpURLConnection doesn't support POST??", e);
        }
        urlc.setDoOutput(true);
        urlc.setDoInput(true);
        urlc.setUseCaches(false);
        urlc.setAllowUserInteraction(false);
        urlc.setRequestProperty("Content-type", "text/xml; charset=" + "UTF-8");

        OutputStream out = urlc.getOutputStream();

        try
        {
            writer = new OutputStreamWriter(out, "UTF-8");
            pipe((byte)1);
            writer.close();
        } catch (IOException e)
        {
             throw new Exception("IOException while posting data", e);
        } finally
        {
            if (out != null)
            out.close();
        }

        InputStream in = urlc.getInputStream();
        
        try
        {
            reader = new InputStreamReader(in);
            pipe((byte)2);
            reader.close();
        } catch (IOException e)
        {
             throw new Exception("IOException while reading response", e);
        } finally
        {
            if (in != null)
            in.close();
        }

    } catch (IOException e)
    {
          throw new Exception("Connection error (is server running at " + endpoint + " ?): " + e);
    } finally
    {
        if (urlc != null)
        urlc.disconnect();
    }
    return ans;
}

/**
* Pipes everything from the reader to the writer via a buffer
*/
private static void pipe(byte mode) throws IOException
{
    char[] buf = new char[1024];
    int read = 0;
    //1 - post
    //2 - receive ans
    if (mode == 1)
    {
        writer.write(rq);
        writer.flush();
    } else
    {
        StringBuilder sbuild = new StringBuilder();
         while ((read = reader.read(buf)) >= 0)
         {
             sbuild.append(buf,0,read);
         }
        ans = sbuild.toString();
    }
}

}
