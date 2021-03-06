package net.geertvos.gvm.ast;

import org.parboiled.support.Position;

import net.geertvos.gvm.compiler.GScriptCompiler;
import net.geertvos.gvm.core.GVM;
import net.geertvos.gvm.streams.RandomAccessByteStream;

public abstract class JumpStatement extends Statement {

	protected JumpStatement(Position pos) {
		super(pos);
	}

	protected int jumpPos;
	protected RandomAccessByteStream code;
	
	public void setJump( int jump )
	{
		int oldpos = code.getPointerPosition();
		code.seek(jumpPos);
		code.writeInt(jump);
		code.seek(oldpos);
	}
	
	@Override
	public void compile(GScriptCompiler c) {
		super.compile(c);
		c.code.write(GVM.JMP);
		jumpPos = c.code.size();
		c.code.writeInt(-1);
		this.code = c.code;
	}

}
