/**
 * 
 */
package org.duodo.netty3ext.service.manager;

import java.util.List;

import org.duodo.netty3ext.service.Service;

/**
 * @author huzorro(huzorro@gmail.com)
 *
 */
public interface StartServices extends Service{
	public List<String> duplexstreamService(List<String> configList);
	public List<String> duplexstreamService() ;
	public List<String> upstreamService(List<String> configList);
	public List<String> upstreamService();
	public List<String> downstreamService(List<String> configList);
	public List<String> downstreamService();
}
