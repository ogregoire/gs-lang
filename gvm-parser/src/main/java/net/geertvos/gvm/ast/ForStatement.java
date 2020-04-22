package net.geertvos.gvm.ast;

import java.util.LinkedList;
import java.util.List;

import net.geertvos.gvm.compiler.GCompiler;
import nl.gvm.core.GVM;

public class ForStatement extends LoopStatement implements Scope {

	private final Expression condition;
	private final Statement initstatement;
	private final Statement updatestatement;
	private List<Statement> loop = new LinkedList<Statement>();

	public ForStatement( Statement update , Expression condition , Statement init )
	{
		this.initstatement = init;
		this.condition = condition;
		this.updatestatement = update;
	}

	
	@Override
	public void compile(GCompiler c) {
		initstatement.compile(c);
		int conditionpos = c.code.size();
		condition.compile(c);
		c.code.add( GVM.NOT );
		c.code.add( GVM.CJMP );
		int elsepos = c.code.size();
		c.code.writeInt( -1 ); 
		for(Statement s : loop) {
			s.compile(c); //TODO: check this after changes made
		}
		int updatepos = c.code.size();
		updatestatement.compile(c);
		c.code.add( GVM.JMP );
		c.code.writeInt( conditionpos );
		c.code.set( elsepos, c.code.size());

		int endPos = c.code.size();
		for( JumpStatement js : breaks )
			js.setJump(endPos);
		for( JumpStatement js : continues )
			js.setJump(updatepos);
		
	}


	public Scope addStatement(Statement statement) {
		loop.add(statement);
		return this;
	}


	public Statement getStatement(int index) {
		return loop.get(index);
	}


	public int getStatements() {
		return loop.size();
	}	
	
}