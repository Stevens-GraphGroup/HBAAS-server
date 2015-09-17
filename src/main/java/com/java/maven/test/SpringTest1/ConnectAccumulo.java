package com.java.maven.test.SpringTest1;

import org.apache.accumulo.core.client.*;
import org.apache.accumulo.core.client.security.tokens.PasswordToken;
import org.apache.accumulo.core.data.Key;
import org.apache.accumulo.core.data.Range;
import org.apache.accumulo.core.data.Value;
import org.apache.accumulo.core.security.Authorizations;
import org.junit.*;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConnectAccumulo {
	private static  String username = "root";
	private static  String password = "secret";
	private static  ClientConfiguration myconfig;
	static {
	    String instance = "instance";
	    String host = "b412srv.ece.stevens-tech.edu:2181";
	    int timeout = 10000; // 10 seconds
	    myconfig = ClientConfiguration.loadDefault().withInstance(instance).withZkHosts(host).withZkTimeout(timeout);
	}

	/**
	 * Connect to b412srv Accumulo server.
	 */
	public static  Connector connectToAccumulo() throws AccumuloSecurityException, AccumuloException {
		Instance instance = new ZooKeeperInstance(myconfig);
	    System.out.println("connect to accumulo?");
	    return instance.getConnector(username, new PasswordToken(password));
	}


	private static String Tseq = "Tseq";

	/** input is a list of Accession numbers
	/   output is a map from accession numbers to sequences */
	public static Map<String,String> getSequencesFromAccumulo(List<String> accessionList,Connector conn) throws AccumuloSecurityException, AccumuloException, TableNotFoundException {
	    // Setup Accumulo connector
	    //Connector conn = connectToAccumulo();
	    System.out.println("connect to accumulo");
	    // Setup BatchScanner to read rows that contain the accession numbers from TseqRaw, using 1 thread
	    String TseqRaw = Tseq+"Raw";
	    int numThreads = 1;
	    BatchScanner scan = conn.createBatchScanner(TseqRaw, Authorizations.EMPTY, numThreads);
	    
	    System.out.println("create scanner");
	    
	    List<Range> accessionRanges = new ArrayList<>(accessionList.size());
	    for (String accession : accessionList) {
	        accessionRanges.add(new Range(accession));
	    }
	    scan.setRanges(accessionRanges);

	    Map<String,String> mapAccessionSeq = new HashMap<>(accessionList.size());
	    // Do the scan
	    for(Map.Entry<Key,Value> entry : scan) {
	        System.out.println(entry); // for debugging
	        String accession = entry.getKey().getRow().toString();
	        assert accessionList.contains(accession);                            // sanity check
	        assert entry.getKey().getColumnQualifier().toString().equals("seq"); // sanity check
	        String seq = entry.getValue().toString();
	        //System.out.println(seq);
	        mapAccessionSeq.put(accession,seq);
	    }
	    scan.close();
	    return mapAccessionSeq;
	}
	
}
