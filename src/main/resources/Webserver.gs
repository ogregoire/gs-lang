module NativeObject;
import System;
// Hacks to work around missing features:
// - the .bytes reference on the string is also a bit hacky

linefeed = "\n";
serversocket = native("java.net.ServerSocket","ServerSocket", 8081);

responseheader = "HTTP/1.1 200 OK";
int counter = 0;
while(true) {
	socket = serversocket.accept();
	output = socket.getOutputStream();
	headers = "Connection: close;\n";
	headers += "Content-Length: 42;\n";
	message = responseheader+"\n"+headers+"\n\n"+"<h1>Hello "+counter+"</h1><h6>Served from the GVM"+linefeed+linefeed;
	bytes = message.bytes;
    output.write(bytes);
	output.flush();
	output.close();
	socket.close();
	counter++;
	System.print("Served request "+counter+" from " +socket.getInetAddress().getHostAddress());
}

