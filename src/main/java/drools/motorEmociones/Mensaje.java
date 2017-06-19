package drools.motorEmociones;

import drools.motorEmociones.MotorEmociones.TipoMensaje;

public class Mensaje {
	private TipoMensaje tipo;
	private Object arg1;
	private Object arg2;
	
	public Mensaje(TipoMensaje tipo, Object arg1, Object arg2) {
		this.tipo = tipo;
		this.arg1 = arg1;
		this.arg2 = arg2;
	}
	
	
	
	public TipoMensaje getTipo() {
		return tipo;
	}
	public void setTipo(TipoMensaje tipo) {
		this.tipo = tipo;
	}
	public Object getArg1() {
		return arg1;
	}
	public void setArg1(Object arg1) {
		this.arg1 = arg1;
	}
	public Object getArg2() {
		return arg2;
	}
	public void setArg2(Object arg2) {
		this.arg2 = arg2;
	}

	
}
