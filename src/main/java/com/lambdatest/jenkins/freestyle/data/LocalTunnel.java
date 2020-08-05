package com.lambdatest.jenkins.freestyle.data;

import java.io.Serializable;

import org.kohsuke.stapler.DataBoundConstructor;

public class LocalTunnel implements Serializable {

	private static final long serialVersionUID = 1L;
	private String tunnelName;
	private boolean sharedTunnel;
	private boolean websocketTunnel;
	private String tunnelExtCommand;
	//Download Tunnel on particular location
	private String downloadTunnelPath;
	//Download Tunnel in workspace
	private boolean useWorkspacePath;

	@DataBoundConstructor
	public LocalTunnel(boolean useLocalTunnel,String tunnelName, boolean sharedTunnel,boolean websocketTunnel, String tunnelExtCommand,String downloadTunnelPath, boolean useWorkspacePath) {
		super();
		this.tunnelName = tunnelName;
		this.sharedTunnel = sharedTunnel;
		this.websocketTunnel = websocketTunnel;
		this.tunnelExtCommand = tunnelExtCommand;
		this.downloadTunnelPath = downloadTunnelPath;
		this.useWorkspacePath = useWorkspacePath;
	}

	public String getTunnelName() {
		return tunnelName;
	}

	public void setTunnelName(String tunnelName) {
		this.tunnelName = tunnelName;
	}

	public boolean isSharedTunnel() {
		return sharedTunnel;
	}

	public void setSharedTunnel(boolean sharedTunnel) {
		this.sharedTunnel = sharedTunnel;
	}

	public String getTunnelExtCommand() {
		return tunnelExtCommand;
	}

	public void setTunnelExtCommand(String tunnelExtCommand) {
		this.tunnelExtCommand = tunnelExtCommand;
	}

	public boolean isWebsocketTunnel() {
		return websocketTunnel;
	}

	public void setWebsocketTunnel(boolean websocketTunnel) {
		this.websocketTunnel = websocketTunnel;
	}

	public String getDownloadTunnelPath() {
		return downloadTunnelPath;
	}

	public void setDownloadTunnelPath(String downloadTunnelPath) {
		this.downloadTunnelPath = downloadTunnelPath;
	}

	public boolean isUseWorkspacePath() {
		return useWorkspacePath;
	}

	public void setUseWorkspacePath(boolean useWorkspacePath) {
		this.useWorkspacePath = useWorkspacePath;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("{\ntunnelName=");
		builder.append(tunnelName);
		builder.append(", \nsharedTunnel=");
		builder.append(sharedTunnel);
		builder.append(", \nwebsocketTunnel=");
		builder.append(websocketTunnel);
		builder.append(", \ntunnelExtCommand=");
		builder.append(tunnelExtCommand);
		builder.append(", \ndownloadTunnelPath=");
		builder.append(downloadTunnelPath);
		builder.append(", \nuseWorkspacePath=");
		builder.append(useWorkspacePath);
		builder.append("\n}");
		return builder.toString();
	}

	

}
