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

	public static int INIT_SIZE = 128;
	//最大值，buf长度超过此，恢复长度到	INIT_SIZE
	public static int MAX_SIZE = 512;
	//当分配内存过大，是否要回收
	public static boolean FIX_SIZE =  false;

	static ThreadLocal<SoftReference<SoftReferenceWriter>> cache = new ThreadLocal<SoftReference<SoftReferenceWriter>>(){
		@Override protected SoftReference<SoftReferenceWriter> initialValue() {
			return new  SoftReference(new SoftReferenceWriter(INIT_SIZE));
		}
	};
	public static SoftReferenceWriter local(){
		SoftReference<SoftReferenceWriter> softReference =  cache.get();
		SoftReferenceWriter writer = softReference.get();
		if(writer==null){
			writer =  new SoftReferenceWriter(INIT_SIZE);
			cache.set(new SoftReference<>(writer));
		}
		return writer;
	}

	public SoftReferenceWriter(int size){
		super(size);
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
		pos = 0;
		if(FIX_SIZE&&buf.length>MAX_SIZE){
			this.buf = new char[INIT_SIZE];
		}
	}
}
