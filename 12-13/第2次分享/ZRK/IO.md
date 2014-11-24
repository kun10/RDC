#Java IO流

###1.Java中的流，可以从不同的角度进行分类。
>	按照数据流的方向不同可以分为：输入流和输出流。
	按照处理数据单位不同可以分为：字节流和字符流。
	 字节流：一次读入或读出是8位二进制。
	 字符流：一次读入或读出是16位二进制。
	按照实现功能不同可以分为：节点流和处理流。
	
###2.字节流抽象基类：InputStream, OutputStream

InputStream继承图
>|--InputStream 
	|--ByteArrayStream
	|--FileInputStream
	|--ObjectInputStream
	|--FilterInputStream
		|--BufferedInputStream
		|--DataInputStream
		|--LineNumberInputStream
		|--PushbackInputStream
	|--SequenceInputStream
	|--StringBufferInputStream
		
OutputStream继承图
>|--OutputStream
	|--ByteArrayOutputStream
	|--FileOutputStream
	|--ObjectOutputStream
	|--PipedOutputStream
	|--FilterOutputStream
	   	|--BufferedOutputStream
	   	|--DataOutputStream
	  	|--PrintStream


###3.字符流抽象基类：Reader, Writer	
***Writer***
Writer抽象类共性:
1.子类必须实现的抽象方法仅有 write(char[], int, int)、flush() 和 close() 

2.常用已实现方法： void write(int c),  void write(String str), void write(char[] cbuf), void write(String str, int off 相对初始写入字符的偏移量, int len 要写入的字符数)
	 
demo1：
 	FileWriter fw = new FileWriter("D:\\1.txt");
	//写入到流中（内存中）,需要flush()方法刷新到目的地
 	fw.write("hello");
 	fw.flush;
 	
demo2：
	FileWriter fw = new FileWriter("D:\\1.txt");
 	fw.write("hello");
	//close()方法关闭流之前会先刷新缓冲区 	
 	fw.close;

demo3：
	//对关闭的流进行不为空的判断
	FilerWriter w = null;
	try {
		w = new FileWriter("D:\\1.txt");
		w.write("hello");
	} catch (IOException e) {
		e.printStackTrace();
	}finally{
		try {
			//需要加一个判断，如果w的初始化抛出异常，那么w为null,调用close方法会抛出异常;如果有多个流需要关闭,关闭前单独用if判断是否为空
			if(w!=null){
				w.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	 

demo4：
	//续写文件,后面加上参数true,向已有文件的末尾加上数据而不是覆盖原文件
	FileWriter fw = new FileWriter("D:\\1.txt", ture);
	fw.write("world");
	fw.close();

demo5:
	//BufferedWriter将文本写入字符输出流，缓冲各个字符，从而提供单个字符、数组和字符串的高效写入。 
	//可以指定缓冲区的大小，或者接受默认的大小。在大多数情况下，默认值就足够大了。
	
	FileWriter fw = new FileWriter("D:\\1.txt");
	
	//为了提高字符写入流效率加入缓冲技术
	BufferedWriter bufw = new BufferedWriter(fw);
	
	bufw.write("abcde");
	//换行，跨平台
	bufw.newLine();
	bufw.write("fgh");
	
	//只要用到缓冲区，就要记得刷新
	bufw.flush();
	//其实关闭缓冲区，就是在关闭缓冲区中的流对象，所以不用再写fw.close()
	bufw.close();
				
####Reader####

Reader继承关系图：

>|--Reader
	|--BufferedReader
		|--LineNumberReader

Reader抽象类共性：
1.子类必须实现的方法只有 read(char[], int, int) 将字符读入数组的某一部分 和 close()
	
2.常用已实现的方法：int read() 读取单个字符, int read(char[] cbuf) 将字符读入数组 
	
demo1:
	//创建一个文件读取流对象和目标文件想关联，要保证目标文件是存在的，否则抛出FileNotFoundException异常
	FileReader fr = new FileReader("D:\\1.txt");
	//read()一次读取一个字符，会自动往下读下一个，返回int,如果读完，返回-1
	char ch1 = (char)fr.read();
	char ch2 = (char)fr.read();
 
demo2:
	FileReader fr = new FileReader("D:\\1.txt");
	//定义一个字符数组，用于存储读到字符，该read(char[])返回的是读到的字符个数。
	char[] buf = new char[1024];
	
	int len = 0;
	while((len=fr.read(buf))!=-1){
		System.out.print(new String(buf, 0, len));
	}
	
demo3:	
	FileWriter w = null;
	FileReader r = null;
	try {
		w = new FileWriter("D://说明文档222.txt");
		r = new FileReader("D://说明文档.txt");
		
		char[] buf = new char[1024];
		int len = 0;
		while((len=r.read(buf))!=-1){
			w.write(buf, 0, len);
		}

	} catch (IOException e) {
		e.printStackTrace();
	}finally{
		try {
			if(w!=null){
				w.close();
			}
			if(r!=null){
				r.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
demo4:
	//从字符输入流中读取文本，缓冲各个字符，从而实现字符、数组和行的高效读取。 
	//可以指定缓冲区的大小，或者可使用默认的大小。大多数情况下，默认值就足够大了。
	
	FileReader fr = new FileReader("D:\\1.txt");
	
	//为了提高效率，加入缓冲技术
	BufferedReader bufr = new BufferedReader(fr);
	
	//读取一个字符
	bufr.read();
	//读取一行
	bufr.readLine();
	
	//readLine()返回：包含该行内容的字符串，不包含任何行终止符，如果已到达流末尾，则返回 null 
	String line = null;
	while((line=bufr.readLine())!=null){
		System.out.print(line);
	}
	
demo5:
	//用缓冲技术实现文本文件复制
	BufferedWriter bufw = null;
	BufferedReader bufr = null;
	try {
		bufw = new BufferedWriter(new FileWriter("D://1_copy.txt"));
		bufr = new BufferedReader(new FileReader("D://1.txt"));
		
		String line = null;
		while((line=bufr.readLine())!=null){
			bufw.write(line);
			bufw.newLine();
			bufw.flush();
		}
		
	} catch (IOException e) {
		e.printStackTrace();
	}finally{
		try {
			if(bufw!=null){
				bufw.close();
			}
			if(bufr!=null){
				bufr.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
demo6:
	//LineNumberReader用于装饰Reader类，增加显示行号功能
	LineNumberReader r = new LineNumberReader(new FileReader("D://1.txt"));
	
	String line = null;
	r.setLineNumber(10);	//从11开始计行号，如果没有设置行号，行号默认按照从1开始 
	while((line=r.readLine())!=null){
		w.write(r.getLineNumber()+":");		//获取行号
		w.write(line);
		w.newLine();
	}
	
demo7:
	//读取键盘录入转为大写，在控制台输出
	//使用转化流	InputStreamReader和OutputStreamWriter
	//InputStreamReader按照指定编码表读取文件,构造方法为InputStreamReader(InputStream in, String charsetName) ,有子类FileReader，只能按照GBK编码表读取
	BufferedReader bufr = new BufferedReader(new InputStreamReader(System.in));
	BufferedWriter bufw = new BufferedWriter(new OutputStreamWriter(System.out));
	String line = null;
	While((line=bufr.readLine())!=null){
		if("over".equals(line)){
			break;
		}
		bufw.write(line.toUpperCase());
		bufw.newLine();
		bufw.flush();
	}
	bufr.close();
	bufw.close();
		
					

###4.对管道进行操作
>PipedInputStream（字节输入流）,PipedOutStream（字节输出流），PipedReader（字符输入流），PipedWriter（字符输出流）
