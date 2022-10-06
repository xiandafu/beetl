package org.beetl.core.io;

import java.lang.ref.SoftReference;

/**
 * 一个缓存Writer,降低内存每次分配和扩展,能较大程度提高性能。 使用风险是如果toString或者close方法没有被调用，
 * 则渲染内容会被保存,因此需要调用toString或者close
 * <pre>
 * try(SoftReferenceWriter sw = SoftReferenceWriter.local()){
 * 			template.renderTo(sw);
 * 			return sw.toString();
 * }
 * </pre>
 * @see SoftReferenceWriter#clearCache
 */
public class SoftReferenceWriter extends  NoLockStringWriter{

	public static int INIT_SIZE = 256;
	//最大值，buf长度超过此，恢复长度到	INIT_SIZE
	public static int MAX_SIZE = 1024*8;

	boolean  used = false;
	int max;

	static ThreadLocal<SoftReference<SoftReferenceWriter>> cache = new ThreadLocal<SoftReference<SoftReferenceWriter>>(){
		@Override protected SoftReference<SoftReferenceWriter> initialValue() {
			return null;
		}
	};

	public static SoftReferenceWriter local(int size){
		SoftReference<SoftReferenceWriter> softReference =  cache.get();
		if(softReference==null){
			return init(size);
		}
		SoftReferenceWriter writer = softReference.get();
		if(writer==null){
			return init(size);

		}else if(writer.used){
			//返回新的。一个线程内，模板渲染内调用了俩次此方法，后面的线程使用新的writer，比如tag标签
			return  new SoftReferenceWriter(INIT_SIZE,size);
		}
		else{
			writer.used = true;
			//检测buf是否为空，参考clearCache
			if(writer.buf==null){
				writer.buf = new char[INIT_SIZE];
			}
			return writer;
		}
	}

	private  static SoftReferenceWriter init(int max){
		SoftReferenceWriter writer =  new SoftReferenceWriter(INIT_SIZE,max);
		writer.used = true;
		cache.set(new SoftReference<>(writer));
		return writer;
	}
	public static SoftReferenceWriter local(){
		return local(MAX_SIZE);
	}

	public SoftReferenceWriter(int size,int max){
		super(size);
		this.max = max;
	}

	@Override
	public String toString() {
		String str =  new String(buf, 0, pos);
		clearCache();
		return  str;
	}

	@Override
	public void close()  {
		clearCache();
	}

	protected  void clearCache(){
		if(used){
			pos = 0;
			used = false;
			if(buf.length>max){
				this.buf = null;
			}
		}
	}
}
