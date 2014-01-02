package org.duodo.netty3ext.manager;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;
import org.duodo.netty3ext.global.GlobalVars;
import org.duodo.netty3ext.service.manager.DefaultClearLogService;
import org.duodo.netty3ext.service.manager.DefaultClientStartService;
import org.duodo.netty3ext.service.manager.DefaultKickService;
import org.duodo.netty3ext.service.manager.DefaultServerStartService;
import org.duodo.netty3ext.service.manager.DefaultStatusService;
import org.duodo.netty3ext.service.manager.DefaultStopService;
import org.duodo.netty3ext.service.manager.StartServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * The default command management services
 * @author huzorro(huzorro@gmail.com)
 *
 */
public class DefaultCommandService  {
	private final static Logger logger= LoggerFactory.getLogger(DefaultCommandService.class);
	private final static StartServices clientService = new DefaultClientStartService(
			GlobalVars.config);
	private final static StartServices serverService = new DefaultServerStartService(
			GlobalVars.config);
	private final static DefaultStatusService upstreamStatusService = new DefaultStatusService(
			GlobalVars.upstreamServicesRunningList, 
			GlobalVars.upstreamSessionConfigMap, 
			GlobalVars.sessionPoolMap,
			GlobalVars.receiveMsgQueueMap, 
			GlobalVars.responseMsgQueueMap, 
			GlobalVars.deliverMsgQueueMap,
			GlobalVars.reserveMsgQueueMap);
	private final static DefaultStatusService downstreamStatusService = new DefaultStatusService(
			GlobalVars.downstreamServicesRunningList, 
			GlobalVars.downstreamSessionConfigMap, 
			GlobalVars.sessionPoolMap,
			GlobalVars.receiveMsgQueueMap, 
			GlobalVars.responseMsgQueueMap, 
			GlobalVars.deliverMsgQueueMap,
			GlobalVars.reserveMsgQueueMap);
	private final static DefaultStatusService duplexstreamStatusService = new DefaultStatusService(
			GlobalVars.duplexstreamServicesRunningList, 
			GlobalVars.duplexSessionConfigMap, 
			GlobalVars.sessionPoolMap,
			GlobalVars.receiveMsgQueueMap, 
			GlobalVars.responseMsgQueueMap, 
			GlobalVars.deliverMsgQueueMap,
			GlobalVars.reserveMsgQueueMap);	
	private final static DefaultStopService upstreamStopService = new DefaultStopService(
			GlobalVars.upstreamServicesRunningList, 
			GlobalVars.upstreamSessionConfigMap, 
			GlobalVars.sessionPoolMap, 
			GlobalVars.executorServiceMap, 
			GlobalVars.scheduleExecutorMap, 
			GlobalVars.externalScheduleExecutorMap, 
			GlobalVars.clientBootstrapMap, 
			GlobalVars.serverBootstrapMap,
			GlobalVars.pluginManagerUtilMap,
			GlobalVars.sessionFactoryMap, 
			GlobalVars.receiveMsgQueueMap,
			GlobalVars.responseMsgQueueMap, 
			GlobalVars.deliverMsgQueueMap,
			GlobalVars.reserveMsgQueueMap);
	private final static DefaultStopService downstreamStopService = new DefaultStopService(
			GlobalVars.downstreamServicesRunningList, 
			GlobalVars.downstreamSessionConfigMap, 
			GlobalVars.sessionPoolMap, 
			GlobalVars.executorServiceMap, 
			GlobalVars.scheduleExecutorMap, 
			GlobalVars.externalScheduleExecutorMap, 
			GlobalVars.clientBootstrapMap, 
			GlobalVars.serverBootstrapMap,
			GlobalVars.pluginManagerUtilMap,
			GlobalVars.sessionFactoryMap,   
			GlobalVars.receiveMsgQueueMap,  
			GlobalVars.responseMsgQueueMap, 
			GlobalVars.deliverMsgQueueMap,  
			GlobalVars.reserveMsgQueueMap);
	private final static DefaultStopService duplexstreamStopService = new DefaultStopService(
			GlobalVars.duplexstreamServicesRunningList, 
			GlobalVars.duplexSessionConfigMap, 
			GlobalVars.sessionPoolMap, 
			GlobalVars.executorServiceMap, 
			GlobalVars.scheduleExecutorMap, 
			GlobalVars.externalScheduleExecutorMap, 
			GlobalVars.clientBootstrapMap, 
			GlobalVars.serverBootstrapMap,
			GlobalVars.pluginManagerUtilMap,
			GlobalVars.sessionFactoryMap,   
			GlobalVars.receiveMsgQueueMap,  
			GlobalVars.responseMsgQueueMap, 
			GlobalVars.deliverMsgQueueMap,  
			GlobalVars.reserveMsgQueueMap); 
	private final static DefaultKickService upstreamKickService = new DefaultKickService(
			GlobalVars.upstreamServicesRunningList, 
			GlobalVars.upstreamSessionConfigMap, 
			GlobalVars.sessionPoolMap);
	private final static DefaultKickService downstreamKickService = new DefaultKickService(
			GlobalVars.downstreamServicesRunningList, 
			GlobalVars.downstreamSessionConfigMap, 
			GlobalVars.sessionPoolMap);
	private final static DefaultKickService duplexstreamKickService = new DefaultKickService(
			GlobalVars.duplexstreamServicesRunningList, 
			GlobalVars.duplexSessionConfigMap, 
			GlobalVars.sessionPoolMap);
	private final static DefaultClearLogService upstreamClearLogService = new DefaultClearLogService(
			GlobalVars.upstreamServicesRunningList, 
			GlobalVars.upstreamSessionConfigMap, 
			GlobalVars.receiveMsgQueueMap, 
			GlobalVars.responseMsgQueueMap, 
			GlobalVars.deliverMsgQueueMap, 
			GlobalVars.reserveMsgQueueMap);
	
	private final static DefaultClearLogService downstreamClearLogService = new DefaultClearLogService(
			GlobalVars.downstreamServicesRunningList, 
			GlobalVars.downstreamSessionConfigMap, 
			GlobalVars.receiveMsgQueueMap, 
			GlobalVars.responseMsgQueueMap, 
			GlobalVars.deliverMsgQueueMap, 
			GlobalVars.reserveMsgQueueMap);	
	private final static DefaultClearLogService duplexstreamClearLogService = new DefaultClearLogService(
			GlobalVars.duplexstreamServicesRunningList, 
			GlobalVars.duplexSessionConfigMap, 
			GlobalVars.receiveMsgQueueMap, 
			GlobalVars.responseMsgQueueMap, 
			GlobalVars.deliverMsgQueueMap, 
			GlobalVars.reserveMsgQueueMap);	
	/**
	 * start	-up		<channelIds, .../all>	upstream service start
	 *			-down	<channelIds, .../all>	downstream service start	
	 *			-duplex	<channelIds, .../all>	duplexstream service start
	 *			-h								print help for the command
	 * @param args
	 * @return
	 */
	@SuppressWarnings("static-access")
	public static List<String> start(String[] args) {
		Options options = new Options();
		options.addOption("h", false, "print help for the command");
		options.addOption("server", false, "server mode");
		options.addOption("client", false, "client mode");
		
		options.addOption(
				OptionBuilder.withLongOpt("upstream")
				.withArgName("channelIds, .../all")
				.hasArgs()
				.withValueSeparator(',')
				.withDescription("start upstream service")
				.create("up"));
		options.addOption(
				OptionBuilder.withLongOpt("downstream")
				.withArgName("channelIds, .../all")
				.hasArgs()
				.withValueSeparator(',')
				.withDescription("start downstream service")
				.create("down"));
		options.addOption(
				OptionBuilder.withLongOpt("duplexstream")
				.withArgName("channelIds, .../all")
				.hasArgs()
				.withValueSeparator(',')
				.withDescription("start duplexstream service")
				.create("duplex"));
		List<String> resultList = new ArrayList<String>();
		CommandLine cli = null;
		try {
			cli = new PosixParser().parse(options, args);
		} catch (ParseException e) {
			logger.error("args parser [FAILS]", e.getCause() != null ? e.getCause() : e);
			resultList.add("args parser [FAILS]");
			return resultList;
		}
		if(cli.hasOption("h")) {
    		HelpFormatter helpFormatter = new HelpFormatter();
    		StringWriter stringWriter = new StringWriter();
    		PrintWriter printWriter = new PrintWriter(stringWriter);
    		helpFormatter.printUsage(printWriter, 80, "start", options);
    		resultList.add(stringWriter.toString());
    		return resultList;
		}
		StartServices startService = cli.hasOption("client") ? clientService
				: (cli.hasOption("server") ? serverService : null);	
		
		if(cli.hasOption("up")) {
			List<String> optionsValues = Arrays.asList(cli.getOptionValues("up"));
			if(optionsValues.contains("all")) {
				resultList.addAll(startService.upstreamService());
			} else {
				resultList.addAll(startService.upstreamService(optionsValues));
			}
		}
		if(cli.hasOption("down")) {
			List<String> optionsValues = Arrays.asList(cli.getOptionValues("down"));
			if(optionsValues.contains("all")) {
				resultList.addAll(startService.downstreamService());
			} else {
				resultList.addAll(startService.downstreamService(optionsValues));
			}			
		}
		if(cli.hasOption("duplex")) {
			List<String> optionsValues = Arrays.asList(cli.getOptionValues("duplex"));
			if(optionsValues.contains("all")) {
				resultList.addAll(startService.duplexstreamService());

			} else {
				resultList.addAll(startService.duplexstreamService(optionsValues));
			}	
		}
		return resultList;
	}
	@SuppressWarnings("static-access")
	public static List<String> stop(String[] args) {
		Options options = new Options();
		options.addOption("h", false, "print help for the command");
		
		options.addOption(
				OptionBuilder.withLongOpt("upstream")
				.withArgName("channelIds, .../all")
				.hasArgs()
				.withValueSeparator(',')
				.withDescription("stop upstream service")
				.create("up"));
		options.addOption(
				OptionBuilder.withLongOpt("downstream")
				.withArgName("channelIds, .../all")
				.hasArgs()
				.withValueSeparator(',')
				.withDescription("stop downstream service")
				.create("down"));
		options.addOption(
				OptionBuilder.withLongOpt("duplexstream")
				.withArgName("channelIds, .../all")
				.hasArgs()
				.withValueSeparator(',')
				.withDescription("stop duplexstream service")
				.create("duplex"));
		List<String> resultList = new ArrayList<String>();
		CommandLine cli = null;
		try {
			cli = new PosixParser().parse(options, args);
		} catch (ParseException e) {
			logger.error("args parser [FAILS]", e.getCause() != null ? e.getCause() : e);
			resultList.add("args parser [FAILS]");
			return resultList;
		}
		if(cli.hasOption("h")) {
    		HelpFormatter helpFormatter = new HelpFormatter();
    		StringWriter stringWriter = new StringWriter();
    		PrintWriter printWriter = new PrintWriter(stringWriter);
    		helpFormatter.printUsage(printWriter, 80, "stop", options);
    		resultList.add(stringWriter.toString());
    		return resultList;
		};
		if(cli.hasOption("up")) {
			List<String> optionsValues = Arrays.asList(cli.getOptionValues("up"));
			if(optionsValues.contains("all")) {
				resultList.addAll(upstreamStopService.stop());
			} else {
				resultList.addAll(upstreamStopService.stop(optionsValues));
			}
		}
		if(cli.hasOption("down")) {
			List<String> optionsValues = Arrays.asList(cli.getOptionValues("down"));
			if(optionsValues.contains("all")) {
				resultList.addAll(downstreamStopService.stop());
			} else {
				resultList.addAll(downstreamStopService.stop(optionsValues));
			}			
		}
		if(cli.hasOption("duplex")) {
			List<String> optionsValues = Arrays.asList(cli.getOptionValues("duplex"));
			if(optionsValues.contains("all")) {
				resultList.addAll(duplexstreamStopService.stop());
			} else {
				resultList.addAll(duplexstreamStopService.stop(optionsValues));
			}	
		}		
		return resultList;
	}
	@SuppressWarnings("static-access")
	public static List<String> status(String[] args) {
		Options options = new Options();
		options.addOption("h", false, "print help for the command");
		
		options.addOption(
				OptionBuilder.withLongOpt("upstream")
				.withArgName("channelIds, .../all")
				.hasArgs()
				.withValueSeparator(',')
				.withDescription("view status for upstream service")
				.create("up"));
		options.addOption(
				OptionBuilder.withLongOpt("downstream")
				.withArgName("channelIds, .../all")
				.hasArgs()
				.withValueSeparator(',')
				.withDescription("view status for downstream service status")
				.create("down"));
		options.addOption(
				OptionBuilder.withLongOpt("duplexstream")
				.withArgName("channelIds, .../all")
				.hasArgs()
				.withValueSeparator(',')
				.withDescription("view status for duplexstream service")
				.create("duplex"));
		List<String> resultList = new ArrayList<String>();
		CommandLine cli = null;
		try {
			cli = new PosixParser().parse(options, args);
		} catch (ParseException e) {
			logger.error("args parser [FAILS]", e.getCause() != null ? e.getCause() : e);
			resultList.add("args parser [FAILS]");
			return resultList;
		}
		if(cli.hasOption("h")) {
    		HelpFormatter helpFormatter = new HelpFormatter();
    		StringWriter stringWriter = new StringWriter();
    		PrintWriter printWriter = new PrintWriter(stringWriter);
    		helpFormatter.printUsage(printWriter, 80, "status", options);
    		resultList.add(stringWriter.toString());
    		return resultList;
		}
		if(cli.hasOption("up")) {
			List<String> optionsValues = Arrays.asList(cli.getOptionValues("up"));
			if(optionsValues.contains("all")) {
				resultList.addAll(upstreamStatusService.statusToStr());
			} else {
				resultList.addAll(upstreamStatusService.statusToStr(optionsValues));
			}
		}
		if(cli.hasOption("down")) {
			List<String> optionsValues = Arrays.asList(cli.getOptionValues("down"));
			if(optionsValues.contains("all")) {
				resultList.addAll(downstreamStatusService.statusToStr());
			} else {
				resultList.addAll(downstreamStatusService.statusToStr(optionsValues));
			}			
		}
		if(cli.hasOption("duplex")) {
			List<String> optionsValues = Arrays.asList(cli.getOptionValues("duplex"));
			if(optionsValues.contains("all")) {
				resultList.addAll(duplexstreamStatusService.statusToStr());
			} else {
				resultList.addAll(duplexstreamStatusService.statusToStr(optionsValues));
			}	
		}		
		return resultList;
	}
	@SuppressWarnings("static-access")
	public static List<String> kick(String[] args) {
		Options options = new Options();
		options.addOption("h", false, "print help for the command");
		
		options.addOption(
				OptionBuilder.withLongOpt("upstream")
				.withArgName("channelIds, .../all")
				.hasArgs()
				.withValueSeparator(',')
				.withDescription("kick session for upstream service")
				.create("up"));
		options.addOption(
				OptionBuilder.withLongOpt("downstream")
				.withArgName("channelIds, .../all")
				.hasArgs()
				.withValueSeparator(',')
				.withDescription("kick session for downstream service")
				.create("down"));
		options.addOption(
				OptionBuilder.withLongOpt("duplexstream")
				.withArgName("channelIds, .../all")
				.hasArgs()
				.withValueSeparator(',')
				.withDescription("kick session for duplexstream service")
				.create("duplex"));
		List<String> resultList = new ArrayList<String>();
		CommandLine cli = null;
		try {
			cli = new PosixParser().parse(options, args);
		} catch (ParseException e) {
			logger.error("args parser [FAILS]", e.getCause() != null ? e.getCause() : e);
			resultList.add("args parser [FAILS]");
			return resultList;
		}
		if(cli.hasOption("h")) {
    		HelpFormatter helpFormatter = new HelpFormatter();
    		StringWriter stringWriter = new StringWriter();
    		PrintWriter printWriter = new PrintWriter(stringWriter);
    		helpFormatter.printUsage(printWriter, 80, "kick", options);
    		resultList.add(stringWriter.toString());
    		return resultList;
		}
		if(cli.hasOption("up")) {
			List<String> optionsValues = Arrays.asList(cli.getOptionValues("up"));
			if(optionsValues.contains("all")) {
				resultList.addAll(upstreamKickService.kick());
			} else {
				resultList.addAll(upstreamKickService.kick(optionsValues));
			}
		}
		if(cli.hasOption("down")) {
			List<String> optionsValues = Arrays.asList(cli.getOptionValues("down"));
			if(optionsValues.contains("all")) {
				resultList.addAll(downstreamKickService.kick());
			} else {
				resultList.addAll(downstreamKickService.kick(optionsValues));
			}			
		}
		if(cli.hasOption("duplex")) {
			List<String> optionsValues = Arrays.asList(cli.getOptionValues("duplex"));
			if(optionsValues.contains("all")) {
				resultList.addAll(duplexstreamKickService.kick());
			} else {
				resultList.addAll(duplexstreamKickService.kick(optionsValues));
			}	
		}		
		return resultList;
	}	
	@SuppressWarnings("static-access")
	public static List<String> clear(String[] args) {
		Options options = new Options();
		options.addOption("h", false, "print help for the command");
		
		options.addOption(
				OptionBuilder.withLongOpt("upstream")
				.withArgName("channelIds, .../all")
				.hasArgs()
				.withValueSeparator(',')
				.withDescription("clear log of queue for upstream service")
				.create("up"));
		options.addOption(
				OptionBuilder.withLongOpt("downstream")
				.withArgName("channelIds, .../all")
				.hasArgs()
				.withValueSeparator(',')
				.withDescription("clear log of queue for downstream service")
				.create("down"));
		options.addOption(
				OptionBuilder.withLongOpt("duplexstream")
				.withArgName("channelIds, .../all")
				.hasArgs()
				.withValueSeparator(',')
				.withDescription("clear log of queue for duplexstream service")
				.create("duplex"));
		List<String> resultList = new ArrayList<String>();
		CommandLine cli = null;
		try {
			cli = new PosixParser().parse(options, args);
		} catch (ParseException e) {
			logger.error("args parser [FAILS]", e.getCause() != null ? e.getCause() : e);
			resultList.add("args parser [FAILS]");
			return resultList;
		}
		if(cli.hasOption("h")) {
    		HelpFormatter helpFormatter = new HelpFormatter();
    		StringWriter stringWriter = new StringWriter();
    		PrintWriter printWriter = new PrintWriter(stringWriter);
    		helpFormatter.printUsage(printWriter, 80, "clear", options);
    		resultList.add(stringWriter.toString());
    		return resultList;
		}
		if(cli.hasOption("up")) {
			List<String> optionsValues = Arrays.asList(cli.getOptionValues("up"));
			if(optionsValues.contains("all")) {
				resultList.addAll(upstreamClearLogService.clear());
			} else {
				resultList.addAll(upstreamClearLogService.clear(optionsValues));
			}
		}
		if(cli.hasOption("down")) {
			List<String> optionsValues = Arrays.asList(cli.getOptionValues("down"));
			if(optionsValues.contains("all")) {
				resultList.addAll(downstreamClearLogService.clear());
			} else {
				resultList.addAll(downstreamClearLogService.clear(optionsValues));
			}			
		}
		if(cli.hasOption("duplex")) {
			List<String> optionsValues = Arrays.asList(cli.getOptionValues("duplex"));
			if(optionsValues.contains("all")) {
				resultList.addAll(duplexstreamClearLogService.clear());
			} else {
				resultList.addAll(duplexstreamClearLogService.clear(optionsValues));
			}	
		}		
		return resultList;
	}		
	public static List<String> bye(String[] args) {
		Options options = new Options();
		options.addOption("h", false, "print help for the command");
		List<String> resultList = new ArrayList<String>();
		CommandLine cli = null;
		try {
			cli = new PosixParser().parse(options, args);
		} catch (ParseException e) {
			logger.error("args parser [FAILS]", e.getCause() != null ? e.getCause() : e);
			resultList.add("args parser [FAILS]");
			return resultList;
		}
		if(cli.hasOption("h")) {
    		HelpFormatter helpFormatter = new HelpFormatter();
    		StringWriter stringWriter = new StringWriter();
    		PrintWriter printWriter = new PrintWriter(stringWriter);
    		helpFormatter.printUsage(printWriter, 80, "bye", options);
    		resultList.add(stringWriter.toString());
    		return resultList;
		}
		return resultList;
	}
	public static List<String> shutdown(String[] args) {
		Options options = new Options();
		options.addOption("h", false, "print help for the command");
		options.addOption("n", "now", false, "shutdown for the platform");
		List<String> resultList = new ArrayList<String>();
		CommandLine cli = null;
		try {
			cli = new PosixParser().parse(options, args);
		} catch (ParseException e) {
			logger.error("args parser [FAILS]", e.getCause() != null ? e.getCause() : e);
			resultList.add("args parser [FAILS]");
			return resultList;
		}
		if(cli.hasOption("h")) {
    		HelpFormatter helpFormatter = new HelpFormatter();
    		StringWriter stringWriter = new StringWriter();
    		PrintWriter printWriter = new PrintWriter(stringWriter);
    		helpFormatter.printUsage(printWriter, 80, "shutdown", options);
    		resultList.add(stringWriter.toString());
    		return resultList;
		}
		if(cli.hasOption("n")) {
			Runtime.getRuntime().exit(0);
		}
		return resultList;
	}
	public static List<String> help(String[] args) {
		List<String> resultList = new ArrayList<String>();

		resultList.add(String.format("%1$s%2$35s", "help", "print help list for the platform"));
		resultList.add(String.format("%1$s%2$40s", "start", "start steam service for the platform"));
		resultList.add(String.format("%1$s%2$39s", "stop", "stop steam service for the platform"));
		resultList.add(String.format("%1$s%2$32s", "status", "view status for the platform"));
		resultList.add(String.format("%1$s%2$33s", "kick", "kick session for the platform"));
		resultList.add(String.format("%1$s%2$43s", "clear", "clear log of bdb queue for the platform"));
		resultList.add(String.format("%1$s%2$38s", "bye", "exit command line for the platform"));
		resultList.add(String.format("%1$s%2$42s", "shutdown", "shutdown all services for the platform"));
		return resultList;
	}
}
