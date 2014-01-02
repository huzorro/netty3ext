/**
 * 
 */
package org.duodo.netty3ext.service.manager;

import java.util.List;

import org.duodo.netty3ext.service.Service;

/**
 * 包含了初始化{@code client}服务所需的服务列表
 * <br>其中涉及到的动作均以{@code client}作为主体
 * <br>{@code deliver}动作是指{@code client}向{@code server}提交消息
 * <br>{@code receiver}动作是指接收{@code server}向{@code client}提交的消息
 * <br>{@code response}是指{@code client}向{@code server} {@code deliver}动作后接收到的来自{@code server}的响应消息
 * @author huzorro(huzorro@gmail.com)
 *
 */
public interface ClientServices extends Service{
	/**
	 * 初始化上行数据流链路的全局变量
	 * @return
	 * @throws Exception
	 */
	public ClientServices upstreamGlobalVarsInit() throws Exception;
	/**
	 * @see #upstreamGlobalVarsInit() 
	 * @param configList {@code channelIds} 列表 {000000, 000001}
	 * @return
	 * @throws Exception
	 */
	public ClientServices upstreamGlobalVarsInit(List<String> configList) throws Exception;
	/**
	 * 初始化下行数据流链路的全局变量
	 * @return
	 * @throws Exception
	 */
	public ClientServices downstreamGlobalVarsInit() throws Exception;
	/**
	 * @see #downstreamGlobalVarsInit()
	 * @param configList {@code channelIds 列表 {000000, 000001}
	 * @return
	 * @throws Exception
	 */
	public ClientServices downstreamGlobalVarsInit(List<String> configList) throws Exception;
	/**
	 * 初始化上下行数据流链路的全局变量
	 * @return
	 * @throws Exception
	 */
	public ClientServices duplexstreamGlobalVarsInit() throws Exception; 
	/**
	 * @see #duplexstreamGlobalVarsInit()
	 * @param configList {@code channelIds 列表 {000000, 000001}
	 * @return
	 * @throws Exception
	 */
	public ClientServices duplexstreamGlobalVarsInit(List<String> configList) throws Exception;
	/**
	 * 初始化上行数据流链路服务
	 * @return
	 */
	public Service upstreamServiceInit(); 
	/**
	 * @ #upstreamServiceInit()
	 * @param configList {@code channelIds 列表 {000000, 000001}
	 * @return
	 */
	public Service upstreamServiceInit(List<String> configList);
	/**
	 * 初始化下行数据流链路服务
	 * @return
	 */
	public Service downstreamServiceInit(); 
	/**
	 * @ #downstreamServiceInit()
	 * @param configList {@code channelIds 列表 {000000, 000001}
	 * @return
	 */
	public Service downstreamServiceInit(List<String> configList);
	/**
	 * 初始化上下行数据流链路服务
	 * @return
	 */
	public Service duplexstreamServiceInit(); 
	/**
	 * @see #duplexstreamServiceInit()
	 * @param configList {@code channelIds 列表 {000000, 000001}
	 * @return
	 */
	public Service duplexstreamServiceInit(List<String> configList);
	/**
	 * 初始化{@code client}服务上行链路连接池监控服务
	 * @return
	 */
	public Service upstreamPoolWatchServiceInit();
	/**
	 * @see #upstreamPoolWatchServiceInit()
	 * @param configList {@code channelIds 列表 {000000, 000001}
	 * @return
	 */
	public Service upstreamPoolWatchServiceInit(List<String> configList);
	/**
	 * 初始化{@code client}服务下行链路连接池监控服务
	 * @return
	 */
	public Service downstreamPoolWatchServiceInit();
	/**
	 * @see #downstreamPoolWatchServiceInit()
	 * @param configList
	 * @return
	 */
	public Service downstreamPoolWatchServiceInit(List<String> configList);
	/**
	 * 初始化{@code client}服务上下行链路连接池监控服务
	 * @return
	 */
	public Service duplexstreamPoolWatchServiceInit(); 
	/**
	 * @see #duplexstreamPoolWatchServiceInit()
	 * @param configList
	 * @return
	 */
	public Service duplexstreamPoolWatchServiceInit(List<String> configList);
	/**
	 * 启动{@code deliver}服务
	 * <br>对于{@code client}服务来讲{@code deliver}是用来向{@code server}提交{@code request}消息
	 * @return
	 */
	public Service downstreamDeliverServiceInit();
	/**
	 * @see #downstreamDeliverServiceInit()
	 * @param configList
	 * @return
	 */
	public Service downstreamDeliverServiceInit(List<String> configList);
	/**
	 * @see #downstreamDeliverServiceInit()
	 * @return
	 */
	public Service downstreamReserveDeliverServiceInit();
	/**
	 * @see #downstreamDeliverServiceInit()
	 * @param configList
	 * @return
	 */
	public Service downstreamReserveDeliverServiceInit(List<String> configList);	
	/**
	 * @see #downstreamDeliverServiceInit()
	 * @return
	 */
	public Service duplexstreamDeliverServiceInit();
	/**
	 * @see #downstreamDeliverServiceInit()
	 * @param confiList
	 * @return
	 */
	public Service duplexstreamDeliverServiceInit(List<String> configList);
	/**
	 * @see #downstreamDeliverServiceInit()
	 * @return
	 */
	public Service duplexstreamReserveDeliverServiceInit();
	/**
	 * @see #downstreamDeliverServiceInit()
	 * @param configList
	 * @return
	 */
	public Service duplexstreamReserveDeliverServiceInit(List<String> configList);
	/**
	 * 启动{@code response}服务 
	 * <br>对于{@code client}服务来讲{@code response}是用于向{@code server}{@code deliver}{@code request}消息后{@code server}返回的{@code response}
	 * <br>具体到应用中{@code response}指一个包含了{@code response}的{@code request}消息
	 * @return
	 */
	public Service downstreamResponseMsgPluginManagerServiceInit();
	/**
	 * @see #downstreamResponseMsgPluginManagerServiceInit() 
	 * @param configList
	 * @return
	 */
	public Service downstreamResponseMsgPluginManagerServiceInit(List<String> configList);
	/**
	 * @see #downstreamResponseMsgPluginManagerServiceInit()
	 * @see #upstreamResponseMsgPluginManagerServiceInit()
	 * @return
	 */
	public Service duplexstreamResponseMsgPluginManagerServiceInit();
	/**
	 * @see #downstreamResponseMsgPluginManagerServiceInit()
	 * @see #upstreamResponseMsgPluginManagerServiceInit()
	 * @param configList
	 * @return
	 */
	public Service duplexstreamResponseMsgPluginManagerServiceInit(List<String> configList);
	
	/**
	 * 启动处理{@code receiver}消息的插件管理器 
	 * <br>对于{@code client}服务来讲{@code receiver}是指接收来自{@code server}的{@code request}消息
	 * @return
	 */
	public Service upstreamReceiverMsgPluginManagerServiceInit();
	/**
	 * @see #upstreamReceiverMsgPluginManagerServiceInit()
	 * @param configList
	 * @return
	 */
	public Service upstreamReceiverMsgPluginManagerServiceInit(List<String> configList);

	/**
	 * @see upstreamReceiverMsgPluginManagerServiceInit()
	 * @return
	 */
	public Service duplexstreamReceiverMsgPluginManagerServiceInit();
	/**
	 * @see upstreamReceiverMsgPluginManagerServiceInit()
	 * @param configList
	 * @return
	 */
	public Service duplexstreamReceiverMsgPluginManagerServiceInit(List<String> configList);

	/**
	 * 启动处理{@code deliver}消息的插件管理器
	 * @return
	 */
	public Service downstreamDeliverMsgPluginManagerServiceInit();
	/**
	 * @see #downstreamDeliverMsgPluginManagerServiceInit()
	 * @param configList
	 * @return
	 */
	public Service downstreamDeliverMsgPluginManagerServiceInit(List<String> configList);
	/**
	 * @see downstreamDeliverMsgPluginManagerServiceInit()
	 * @return
	 */
	public Service duplexstreamDeliverMsgPluginManagerServiceInit();
	/**
	 * @see downstreamDeliverMsgPluginManagerServiceInit()
	 * @param configList
	 * @return
	 */
	public Service duplexstreamDeliverMsgPluginManagerServiceInit(List<String> configList);
	

}
