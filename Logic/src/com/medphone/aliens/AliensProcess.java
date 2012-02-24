package com.medphone.aliens;
import com.medphone.Process;


abstract class AliensProcess extends Process {
	AliensEngine a() {
		return (AliensEngine)engine;
	}
}
