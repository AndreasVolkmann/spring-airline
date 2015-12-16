package com.airline.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class IOUtilities
{
    /**
     * Serialization version.
     */
    private static final long serialVersionUID = 1L;

    public  static  final   String  ENCODING_UTF8 = "UTF-8";

    //      attributes

    //      behaviors

    /******************************************************************************
     *   
     * Description    Gets a resource given a resource name.  Attempats are made
     *                 in the folliwng order:
     *                 1) Classloader
     *                 2) Class relative path
     *                 3) Class absolute path
     *                 4) Context classloader
     *
     *      @param    resourceName - the name of the resource.
     *
     *    @returns    A url for the resource.
     *
     *****************************************************************************/
    public static URL getResource( String resourceName ) throws IOException
    {
        // try class loader
        URL resource = IOUtilities.class.getClassLoader().getResource( resourceName);
        if( resource == null ) 
        {
            // try class relative path
            resource = IOUtilities.class.getResource( resourceName );
        }
        if( resource == null ) 
        {
            // try class absolute path
            resource = IOUtilities.class.getResource( "/" + resourceName );
        }

        if (resource == null)
        {
            // try context class loader
            resource = Thread.currentThread().getContextClassLoader().getResource( resourceName );
        }
        
        if (resource == null)
        {
            // try context class loader
            resource = new File( resourceName ).toURI().toURL();
        }
        
        if (resource == null)
        {
            // try context class loader
            resource = new File( "/" + resourceName ).toURI().toURL();
        }
        
        if (resource == null)
        {
            // give up :)
            throw new IOException( "Resource not found.  Resource name: " + resourceName );
        }

        return resource;
    }

    /******************************************************************************
     *   
     * Description    Gets an input stream from a resource.  
     *
     *      @param    resourceName - the name of the resource.
     *
     *    @returns    An input stream for the resource.
     *
     *****************************************************************************/
    public static InputStream getResourceInputStream( String resourceName ) 
    {
        InputStream inputStream = null;
        try{
	        // get resource
	        URL resource = null;
	        resource = IOUtilities.getResource( resourceName );
	
	        if( resource != null )
	        {
	            // get stream
	            inputStream = resource.openStream();
	        }
        }catch(Exception e){
        	
        }
        return inputStream;
    }
    /******************************************************************************
     *   
     * Description    Gets an file from a resource.  
     *
     *      @param    resourceName - the name of the resource.
     * @throws URISyntaxException 
     *
     *    @returns    An file object for the resource.
     *
     *****************************************************************************/
    public static File getResourceFile( String resourceName ) throws IOException, URISyntaxException
    {
        File file = null;

        // get resource
        URL resource = null;
        resource = IOUtilities.getResource( resourceName );

        if( resource != null )
        {
            // get stream
            file = new File(resource.toURI());
        }

        return file;
    }

    /******************************************************************************
     *   
     * Description      
     *
     *      @param    
     *
     *    @returns    
     *
     *	   @throws 	IOException 
     *****************************************************************************/
    public static String readInputStream( InputStream inputStream ) throws IOException
    {
        return IOUtilities.readInputStream( inputStream, IOUtilities.ENCODING_UTF8 );
    }

    /******************************************************************************
     *   
     * Description      
     *
     *      @param    
     *
     *    @returns    
     *
     *	   @throws 	IOException 
     *****************************************************************************/
    public static String readInputStream( InputStream inputStream, String charSet ) throws IOException
    {
        final StringBuilder stringBuilder = new StringBuilder();
        final BufferedReader r = new BufferedReader(new InputStreamReader( inputStream, charSet ));
        String str;

        while( ( str = r.readLine() ) != null )
        {
            stringBuilder.append( str );
            stringBuilder.append( '\n' );
        }

        return stringBuilder.toString();
    }

    /******************************************************************************
     *   
     * Description  Writes an input stream to a file. If any exceptions are 
     *              encountered the operation halts and returns false.  This will 
     *              leave the destination file in an indeterminate state.
     *    
     *     @return  true if successful, false if not.
     * 
     *      @param  destinationFile - the file to copy into.
     *              sourceFile - the file to copy.
     *                  
     *     @throws IOException 
     *
     *****************************************************************************/
    public static boolean streamToFile( InputStream input, File destinationFile, int bufferSize ) throws IOException
    {
        boolean success = false;
        FileOutputStream output = null;

        try {
            // create byte array, input & output streams
            byte[] bytearray = new byte[ bufferSize ];
            output = new FileOutputStream( destinationFile );

            // loop through source stream and give to destination stream
            int len = 0;
            while( ( len = input.read( bytearray ) ) != -1 ) 
            {
                output.write( bytearray, 0, len );
            }

            success = true;
        }
        finally 
        {
            try 
            {
                // clean up
                input.close();
                output.close();
            }
            catch( IOException ioe ) 
            {
                ioe.printStackTrace();
            }
        }

        return success;
    }

    /******************************************************************************
     *   
     * Description  
     * 
     *     @return  
     *     
     *      @param  
     *                  
     *     @throws  NamingException
     *
     *****************************************************************************/
    public static Object jndiLookup( String key ) throws NamingException
    {
        Object value = null;

        Context context = new InitialContext();
        value = context.lookup( key );
        
        return value;
    }

    /******************************************************************************
     *   
     * Description  
     * 
     *     @return  
     *     
     *      @param  
     *                  
     *     @throws  FileNotFoundException
     *     @throws  NamingException 
     *
     *****************************************************************************/
    public static InputStream jndiLookupFileInputStream( String key ) throws FileNotFoundException, NamingException
    {
        // look up path, get file and file input stream
        String filePath = (String)IOUtilities.jndiLookup( key );
        File file = new File( filePath );
        FileInputStream fileInputStream = new FileInputStream( file );
        
        return fileInputStream;
    }
    
    /******************************************************************************
     *   
     * Description  
     * 
     *     @return  
     *     
     *      @param  
     *                  
     *     @throws  NamingException 
     * @throws URISyntaxException 
     * @throws IOException 
     *
     *****************************************************************************/
    public static InputStream lookupFileInputStream( String fileName ) throws NamingException, IOException, URISyntaxException
    {
        // look up path, get file and file input stream
    	File file = IOUtilities.getResourceFile( fileName );
        FileInputStream fileInputStream = new FileInputStream( file );        
        return fileInputStream;
    }
        
    /******************************************************************************
     *   
     * Description  
     * 
     *     @return  
     *     
     *      @param  
     *                  
     *     @throws  FileNotFoundException
     *     @throws  NamingException 
     *
     *****************************************************************************/
    public static File jndiLookupFile( String key ) throws FileNotFoundException, NamingException
    {
        // look up path, get file and file input stream
        String filePath = (String)IOUtilities.jndiLookup( key );
        File file = new File( filePath );
        
        return file;
    }
    /******************************************************************************
     *   
     * Description  
     * 
     *     @return  
     *     
     *      @param  
     *                  
     *     @throws  NamingException 
     *     @throws  IOException 
     *     @throws  FileNotFoundException  
     *
     *****************************************************************************/
    public static Properties jndiLookupProperties( String key ) throws FileNotFoundException, IOException, NamingException
    {
        Properties properties = new Properties();
        properties.load( IOUtilities.jndiLookupFileInputStream( key ) );
        
        return properties;
    }


    /******************************************************************************
     *   
     * Description  
     * 
     *     @return  
     *     
     *      @param  
     *                  
     *     @throws  IOException    
     *
     *****************************************************************************/
    // TODO: add unit test for this!!!
    public static File compressFile( String filePath, String compressedFilePath, int bufferSize ) throws IOException 
    {
        InputStream     inStream       = null;
        ZipOutputStream zipOutStream   = null;
        File            compressedFile = null;
        // create buffer
        byte[] buffer = new byte[ bufferSize ];
        
        try 
        {
            // get file, stream handles
            File file           = new File( filePath           );
                 compressedFile = new File( compressedFilePath );
                 inStream       = new BufferedInputStream( new FileInputStream( file ) );
                 zipOutStream   = new ZipOutputStream( new BufferedOutputStream( new FileOutputStream( compressedFile ) ) );
        
            // add zip entry to output stream
                 zipOutStream.putNextEntry( new ZipEntry( file.getName() ) );
        
            // stream compressed contents from file to compressed file
            int bytesRead;
            while( ( bytesRead = inStream.read( buffer ) ) > 0 ) 
            {
                zipOutStream.write( buffer, 0, bytesRead );
            }
        } 
        finally
        {
            // clean up resources
            if( inStream != null )
            {
                try
                {
                    inStream.close();
                }
                catch( IOException e )
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            
            if( zipOutStream != null )
            {
                try
                {
                    zipOutStream.closeEntry();
                }
                catch( IOException e )
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                try
                {
                    zipOutStream.close();
                }
                catch( IOException e )
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }   
            }
        }   
        
        return compressedFile;
    }

    /******************************************************************************
     *   
     * Description  
     * 
     *     @return  
     *     
     *      @param  
     *                  
     *     @throws  IOException    
     *
     *****************************************************************************
    // TODO: add unit test for this!!!
    public static void copyFile( File originalFile, File newFile, int bufferSize ) throws IOException
    {
        // ensure new file exists
        newFile.createNewFile();
        
        // stream from original file to new
        BufferedInputStream bufferedInputStream = new BufferedInputStream( new FileInputStream( originalFile ) );
        IOUtilities.streamToFile( bufferedInputStream, newFile, bufferSize );
    }
    */
    
    public static void copyFileUsingStream(File source, File dest) throws IOException {
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(source);
            os = new FileOutputStream(dest);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        } finally {
        	if(null != is)
                is.close();
            if(null != os)
            	os.close();
        }
    }
    public static byte[] readFully(File file) throws IOException
    {
    	FileInputStream inputStream = new FileInputStream(file);
        byte[] buffer = new byte[8192];
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1)
        {
            baos.write(buffer, 0, bytesRead);
        }
        return baos.toByteArray();
    }
}
