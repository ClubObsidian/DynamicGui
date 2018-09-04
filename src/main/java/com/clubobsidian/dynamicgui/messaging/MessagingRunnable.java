package com.clubobsidian.dynamicgui.messaging;

import com.clubobsidian.dynamicgui.entity.PlayerWrapper;

public interface MessagingRunnable  {

	public void run(PlayerWrapper<?> playerWrapper, byte[] message);

}