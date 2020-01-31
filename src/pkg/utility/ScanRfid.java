package pkg.utility;

import pkg.properties.ReadProperties;

import com.example.sdksamples.GpiChangeListenerImplementation;
import com.example.sdksamples.ReaderStartListenerImplementation;
import com.example.sdksamples.ReaderStopListenerImplementation;
import com.example.sdksamples.TagReportListenerImplementation;
import com.impinj.octane.AntennaConfigGroup;
import com.impinj.octane.AutoStartMode;
import com.impinj.octane.AutoStopMode;
import com.impinj.octane.FeatureSet;
import com.impinj.octane.GpoConfigGroup;
import com.impinj.octane.GpoMode;
import com.impinj.octane.ImpinjReader;
import com.impinj.octane.OctaneSdkException;
import com.impinj.octane.ReaderMode;
import com.impinj.octane.SearchMode;
import com.impinj.octane.Settings;

import java.sql.*;
import java.util.ArrayList;

public class ScanRfid {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		scan("79.10.132.7");
	}
	
	public static int gpoRead = 1;
	public static boolean errore = false;
	
	public static void scan(String ip) {
		
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		String sql = "";
		
		try {
					
            String hostname = ip;
            
            ImpinjReader reader = new ImpinjReader();

            // Connect
            System.out.println("Connecting to " + hostname);
            reader.connect(hostname);

            // Get the default settings
            Settings settings = reader.queryDefaultSettings();
            
            boolean gpo1=false;
            boolean gpo2=false;
            boolean gpo3=false;
            boolean gpo4=false;
            int trigger = 1;
            int start = 4;
            int read = 2;
            int error = 3;
            String read_mode="N";
            String error_mode="N";
            String start_mode="N";
            int read_pulse = 500;
            int error_pulse = 500;
            int start_pulse = 500;
            
            double power1=30;
            double power2=30;
            double power3=30;
            double power4=30;
            
            double sens1=-70;
            double sens2=-70;
            double sens3=-70;
            double sens4=-70;
            
            
            String readerMode = "";
            String searchMode = "";
            int session = 0;
            String frequencies = "";
            boolean freq1 = false;
            boolean freq2 = false;
            boolean freq3 = false;
            boolean freq4 = false;
            
            GpoConfigGroup gpos = settings.getGpos();
            
            // Spengo i gpo
            reader.setGpo(1, false);
            reader.setGpo(2, false);
            reader.setGpo(3, false);
            reader.setGpo(4, false);
            
            // Li imposto come da db
            reader.setGpo(1, gpo1);
            reader.setGpo(2, gpo2);
            reader.setGpo(3, gpo3);
            reader.setGpo(4, gpo4);   
            
            // connect a listener
            reader.setTagReportListener(new TagReportListenerImplementation());
            
            // turn on these listeners to see how the GPI triggers
            reader.setGpiChangeListener(
                    new GpiChangeListenerImplementation());
            reader.setReaderStopListener(
                    new ReaderStopListenerImplementation());
            reader.setReaderStartListener(
                    new ReaderStartListenerImplementation());
            
            // Imposto il modo del gpo START
            if(start_mode.equals("P")) {
            	gpos.getGpo((short) start).setMode(GpoMode.Pulsed);
                gpos.getGpo((short) start).setGpoPulseDurationMsec(start_pulse);
            }
            
            // Imposto il modo del gpo READ
            if(read_mode.equals("P")) {
            	gpos.getGpo((short) read).setMode(GpoMode.Pulsed);
                gpos.getGpo((short) read).setGpoPulseDurationMsec(read_pulse);
            }
            
            // Imposto il modo del gpo error
            if(error_mode.equals("P")) {
            	gpos.getGpo((short) error).setMode(GpoMode.Pulsed);
                gpos.getGpo((short) error).setGpoPulseDurationMsec(error_pulse);
            }
            
            // include this so we know where it was seen but only when we stop
            // it
            settings.getReport().setIncludeAntennaPortNumber(true);
            
        	// enable this GPI and set some debounce
            settings.getGpis().get(1).setIsEnabled(true);
            settings.getGpis().get(1).setPortNumber(trigger);
            settings.getGpis().get(1).setDebounceInMs(50);
            
            // set autostart to go on GPI level
            settings.getAutoStart().setGpiPortNumber(trigger);
            settings.getAutoStart().setMode(AutoStartMode.GpiTrigger);
            settings.getAutoStart().setGpiLevel(true);
            
            // if you set start, you have to set stop
            settings.getAutoStop().setMode(AutoStopMode.GpiTrigger);
            settings.getAutoStop().setGpiPortNumber(trigger);
            settings.getAutoStop().setGpiLevel(false);
            settings.getAutoStop().setTimeout(999999999);
            
            AntennaConfigGroup ag = settings.getAntennas();
            
            System.out.println("sens1: "+sens1);
            System.out.println("sens2: "+sens2);
            System.out.println("sens3: "+sens3);
            System.out.println("sens4: "+sens4);
            
            ag.disableAll();
            ag.getAntenna((short) 1).setEnabled(true);
            ag.getAntenna((short) 2).setEnabled(true);
            ag.getAntenna((short) 3).setEnabled(true);
            ag.getAntenna((short) 4).setEnabled(true);
            ag.setIsMaxRxSensitivity(false);
           
            ag.getAntenna((short) 1).setIsMaxRxSensitivity(false);
            ag.getAntenna((short) 1).setRxSensitivityinDbm(sens1);
            ag.getAntenna((short) 1).setIsMaxTxPower(false);
            ag.getAntenna((short) 1).setTxPowerinDbm(power1);
            
            
            ag.getAntenna((short) 2).setIsMaxTxPower(false);
            ag.getAntenna((short) 2).setTxPowerinDbm(power2);
            ag.getAntenna((short) 2).setIsMaxRxSensitivity(false);
            ag.getAntenna((short) 2).setRxSensitivityinDbm(sens2);
            
            ag.getAntenna((short) 3).setIsMaxTxPower(false);
            ag.getAntenna((short) 3).setTxPowerinDbm(power3);
            ag.getAntenna((short) 3).setIsMaxRxSensitivity(false);
            ag.getAntenna((short) 3).setRxSensitivityinDbm(sens3);
            
            ag.getAntenna((short) 4).setIsMaxTxPower(false);
            ag.getAntenna((short) 4).setTxPowerinDbm(power4);
            ag.getAntenna((short) 4).setIsMaxRxSensitivity(false);
            ag.getAntenna((short) 4).setRxSensitivityinDbm(sens4);
            
            // Imposto READERMODE
            if(readerMode.trim().length()>0) {
            	switch (readerMode) {
                	case "1":  
                		settings.setReaderMode(ReaderMode.AutoSetDenseReader);
                		break;
                	
                	case "2":  
                		settings.setReaderMode(ReaderMode.AutoSetDenseReaderDeepScan);
                    	break;
                    
                	case "3":  
                		settings.setReaderMode(ReaderMode.AutoSetStaticDRM);
                    	break;
                    	
                	case "4":  
                		settings.setReaderMode(ReaderMode.AutoSetStaticFast);
                    	break;
                    	
                	case "5":  
                		settings.setReaderMode(ReaderMode.DenseReaderM4);
                    	break;
                    	
                	case "6":  
                		settings.setReaderMode(ReaderMode.DenseReaderM4Two);
                    	break;
                    	
                	case "7":  
                		settings.setReaderMode(ReaderMode.DenseReaderM8);
                    	break;
                    	
                	case "8":  
                		settings.setReaderMode(ReaderMode.Hybrid);
                    	break;
                    	
                	case "9":  
                		settings.setReaderMode(ReaderMode.MaxMiller);
                    	break;
                    	
                	case "0":  
                		settings.setReaderMode(ReaderMode.MaxThroughput);
                    	break;
            	}
            }
            
            // Imposto SEARCHMODE
            if(searchMode.trim().length()>0) {
            	switch (searchMode) {
                	case "S":  
                		settings.setSearchMode(SearchMode.SingleTarget);
                		break;
                		
                	case "D":  
                		settings.setSearchMode(SearchMode.DualTarget);
                		break;
            	}
            }
            
            // Imposto SESSION
            if(session>0) {
            	settings.setSession(session);
            }
            
            if(frequencies.equals("L")) {
            	FeatureSet features = reader.queryFeatureSet();
                
            	if (!features.isHoppingRegion()) {
                    // settings fixed frequencies is allowed if its non hopping
                    ArrayList<Double> freqList = new ArrayList<Double>();
                    
                    if(freq1)
                    	freqList.add(865.7);
                    
                    if(freq2)
                    	freqList.add(866.3);
                    
                    if(freq3)
                    	freqList.add(866.9);
                    
                    if(freq4)
                    	freqList.add(867.5);

                    settings.setTxFrequenciesInMhz(freqList);
                }
            }
            
            // Apply the new settings
            reader.applySettings(settings);
            
            // Start the reader
            reader.start();
            
            ReadProperties prop = new ReadProperties("connection.properties");
			String delay = prop.get("delay");
			
            Thread.sleep(Integer.parseInt(delay));
            
            System.out.println("Stopping  " + hostname);
            reader.stop();

            System.out.println("Disconnecting from " + hostname);
            reader.disconnect();

            System.out.println("Done");
            
        } 
        catch (Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace(System.out);
        }
		finally {
			try {
				if(st!=null)
					st.close();
				if(conn!=null)
					conn.close();
			}
			catch(Exception e) {}
		}
	}

}
