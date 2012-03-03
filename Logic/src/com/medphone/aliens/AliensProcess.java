package com.medphone.aliens;
import com.medphone.Process;


public abstract class AliensProcess extends Process {
	protected AliensEngine a() {
		return (AliensEngine)engine;
	}
}
