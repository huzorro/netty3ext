package org.duodo.netty3ext.service.manager;

import java.util.List;

import org.duodo.netty3ext.service.Service;

/**
 * 包含了初始化{@code server}服务所需的服务列表
 * <br>其中涉及到的动作均以{@code server}作为主体
 * <br>{@code receive}动作是指{@code server}接收来自{@code client}提交的消息
 * <br>{@code deliver}动作是指{@code server}向{@code client}提交消息
 * <br>{@code response}是指{@code server}向{@code client} {@code deliver}动作后接收到的来自{@code client}的响应消息
 * @author huzorro(huzorro@gmail.com)
 *
 */
public interface ServerServices extends Service {
	/**
	 * 初始化上行数据流链路的全局变量
	 * @return
	 * @throws Exception
	 */
	public ServerServices upstreamGlobalVarsInit() throws Exception;
	/**
	 * @see #upstreamGlobalVarsInit() 
	 * @param configList {@code channelIds} 列表 {000000, 000001}
	 * @return
	 * @throws Exception
	 */
	public ServerServices upstreamGlobalVarsInit(List<String> configList) throws Exception;
	/**
	 * 初始化下行数据流链路的全局变量
	 * @return
	 * @throws Exception
	 */
	public ServerServices downstreamGlobalVarsInit() throws Exception;
	/**
	 * @see #downstreamGlobalVarsInit()
	 * @param configList {@code channelIds 列表 {000000, 000001}
	 * @return
	 * @throws Exception
	 */
	public ServerServices downstreamGlobalVarsInit(List<String> configList) throws Exception;
	/**
	 * 初始化上下行数据流链路的全局变量
	 * @return
	 * @throws Exception
	 */
	public ServerServices duplexstreamGlobalVarsInit() throws Exception; 
	/**
	 * @see #duplexstreamGlobalVarsInit()
	 * @param configList {@code channelIds 列表 {000000, 000001}
	 * @return
	 * @throws Exception
	 */
	public ServerServices duplexstreamGlobalVarsInit(List<String> configList) throws Exception;
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
	 * 启动下行数据流服务的{@receive}服务
	 * <br>对于{@code server}服务是用来接收来自连接该{@code server}的{@code clinet}提交的{@code request}消息
	 * @return
	 */
	public Service downstreamReceiveMsgPluginManagerServiceInit();
	/**
	 * @see #downstreamReceiveMsgPluginManagerServiceInit()
	 * @param configList
	 * @return
	 */
	public Service downstreamReceiveMsgPluginManagerServiceInit(List<String> configList);
	/**
	 * @see #downstreamReceiveMsgPluginManagerServiceInit()
	 * @return
	 */

	public Service duplexstreamReceiveMsgPluginManagerServiceInit();
	/**
	 * @see #downstreamReceiveMsgPluginManagerServiceInit()
	 * @param confiList
	 * @return
	 */
	public Service duplexstreamReceiveMsgPluginManagerServiceInit(List<String> confiList);
	
	/**
	 * 启动{@code response}服务 
	 * {@code response}是指{@code server}向{@code client} {@code deliver}动作后接收到的来自{@code client}的响应消息
	 * <br>具体到应用中{@code response}指一个包含了{@code response}的{@code request}消息
	 * @return
	 */
	public Service upstreamResponseMsgPluginManagerServiceInit();
	/**
	 * @see #upstreamResponseMsgPluginManagerServiceInit()
	 * @param configList
	 * @return
	 */
	public Service upstreamResponseMsgPluginManagerServiceInit(List<String> configList);
	/**
	 * @see #upstreamResponseMsgPluginManagerServiceInit()
	 * @return
	 */
	public Service duplexstreamResponseMsgPluginManagerServiceInit();
	/**
	 * @see #upstreamResponseMsgPluginManagerServiceInit()
	 * @param configList
	 * @return
	 */
	public Service duplexstreamResponseMsgPluginManagerServiceInit(List<String> configList);
	
	/**
	 * 启动处理{@code deliver}消息的插件管理器 
	 * <br>对于{@code server}服务来讲{@code deliver}是指向连接该{@code server}的{@code client}发送{@code request}消息
	 * @return
	 */
	public Service upstreamDeliverMsgPluginManagerServiceInit();
	/**
	 * @see #upstreamDeliverMsgPluginManagerServiceInit()
	 * @param configList
	 * @return
	 */
	public Service upstreamDeliverMsgPluginManagerServiceInit(List<String> configList);
	/**
	 * @see upstreamDeliverMsgPluginManagerServiceInit()
	 * @return
	 */
	public Service duplexstreamDeliverMsgPluginManagerServiceInit();
	/**
	 * @see upstreamDeliverMsgPluginManagerServiceInit()
	 * @param configList
	 * @return
	 */
	public Service duplexstreamDeliverMsgPluginManagerServiceInit(List<String> configList);
	
	/**
	 * 启动上行数据流链路的{@code deliver}服务
	 * @return
	 */
	public Service upstreamDeliverServiceInit();
	/**
	 * @see #upstreamDeliverServiceInit()
	 * @param configList
	 * @return
	 */
	public Service upstreamDeliverServiceInit(List<String> configList);
	/**
	 * 启动上行数据流链路的备用{@code deliver}服务
	 * @return
	 */
	public Service upstreamReserveDeliverServiceInit();
	/**
	 * @see #upstreamReserveDeliverServiceInit()
	 * @param configList
	 * @return
	 */
	public Service upstreamReserveDeliverServiceInit(List<String> configList);
	/**
	 * @see upstreamDeliverServiceInit()
	 * @return
	 */
	public Service duplexstreamDeliverServiceInit();
	/**
	 * @see upstreamDeliverServiceInit()
	 * @param configList
	 * @return
	 */
	public Service duplexstreamDeliverServiceInit(List<String> configList);
	/**
	 * @see #upstreamReserveDeliverServiceInit()
	 * @return
	 */
	public Service duplexstreamReserveDeliverServiceInit();
	/**
	 * @see #upstreamReserveDeliverServiceInit()
	 * @param configList
	 * @return
	 */
	public Service duplexstreamReserveDeliverServiceInit(List<String> configList);	
}
