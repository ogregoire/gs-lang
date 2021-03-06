package net.geertvos.gvm.ast;

import net.geertvos.gvm.compiler.GScriptCompiler;
import net.geertvos.gvm.core.GVM;
import net.geertvos.gvm.lang.types.ObjectType;
import net.geertvos.gvm.lang.types.StringType;

public class ConstantExpression extends Expression {

	private final String type;
	private int value = -1;
	private final String string;
	
	public ConstantExpression( int value, String type )
	{
		this.value = value;
		this.type = type;
		this.string = null;
	}
	
	@Override
	public String toString() {
		return "ConstantExpression [type=" + type + ", value=" + value + ", string=" + string + "]";
	}

	public ConstantExpression( String s )
	{
		this.string = s.replaceAll("\\\\n", "\n");
		this.type = new StringType().getName();
	}

	public ConstantExpression()
	{
		this.type = new ObjectType().getName();
		this.string = null;
	}	
	
	@Override
	public void compile(GScriptCompiler c) {
		if(string != null) {
			int index = c.getProgram().addString(string);
			c.code.add(GVM.LDC_D);
			c.code.writeInt(index);
			c.code.writeString(new StringType().getName());
			
		}
		else if(value == -1) {
			c.code.add( GVM.NEW);
			c.code.writeString(type);
		} else {
			c.code.add(GVM.LDC_D);
			c.code.writeInt(value);
			c.code.writeString(type);
		}
	}

	public int getValue() {
		return value;
	}

	public String getString() {
		return string;
	}

	public String getType() {
		return type;
	}
	
	
	
}
