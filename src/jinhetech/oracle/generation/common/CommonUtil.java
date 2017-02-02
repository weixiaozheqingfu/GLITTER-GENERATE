package jinhetech.oracle.generation.common;

import java.util.List;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Element;

/**
 * 通用工具类。
 * @author LiMengjun
 * @version V1.0 2015-05-02 初版
 */
public class CommonUtil {
	
	/**
	 * 判断所传可变集合是否全部不为空并且size>0
	 * @param list... 可变参数List<?>
	 * @return
	 */
	public static boolean isNotBlankList(List<?>... list){
		boolean result = true;
		if(list!=null && list.length>0){
			for(int i=0;i<list.length;i++){
				if(list[i]==null || (list[i]!=null && list[i].size()<=0)){
					result = false;
					break;
				}
			}
		}else{
			result = false;
		}
		return result;
	}
	
	/**
	 * 判断所传可变实体是否全部不为空
	 * @param object 可变参数Object实体
	 * @return
	 */
	public static boolean isNotBlankObj(Object... object){
		boolean result = true;
		if(object!=null && object.length>0){
			for(int i=0;i<object.length;i++){
				if(object[i]==null){
					result = false;
					break;
				}
			}
		}else{
			result = false;
		}
		return result;
	}
	
	/**
	 * 判断该字符串是否为空串，若不是空串，则返回为true
	 * @param str
	 * @return boolean 
	 * 	true：该字符串不是空串
	 * 	false：该字符串为null或为空串或为空格 		
	 */
	public static boolean isNotBlank(String str) {
		return str != null && !"".equals(str.trim()) && !"undefined".equalsIgnoreCase(str)&& !"null".equalsIgnoreCase(str);
	}
	

	/**
	 * 判断可变字符串是否全部非空，若不是空串，则返回为true
	 * @param str
	 * @return boolean 
	 * 	true：都不是空串
	 * 	false：有为null或为空的	
	 */
	public static boolean isNotBlank(String... str) {
		boolean result = true;
		if(str!=null && str.length>0){
			for(int i=0;i<str.length;i++){
				if(CommonUtil.isBlank(str[i])){
					result = false;
					break;
				}
			}
		}else{
			result = false;
		}
		return result;
	}
	
	/**
	 * @param src
	 * @return
	 */
	public static boolean isBlank(String src){
		return src==null || "".equals(src.trim());
	}
	
	// 将每一个节点的属性值添加到map中
	@SuppressWarnings("unchecked")
	public static void putMap(Element element,Map<String,Object> map){
		if(CommonUtil.isNotBlankObj(element,map)){
			String keyPrifix = CommonUtil.getKeyPrifix(element);
			keyPrifix = CommonUtil.isBlank(keyPrifix)?"":(keyPrifix+".");
			
			List<Attribute> attributeList = element.attributes();
			if(CommonUtil.isNotBlankList(attributeList)){
				for (int i = 0; i < attributeList.size(); i++) {
				    Attribute item = attributeList.get(i);
				    String key = keyPrifix + element.getName()+"."+item.getName();
				    String value = item.getValue();
//				    System.out.println(key + "=" + value);
				    map.put(key, value);
				}
			}
		}
	}
	
	// 递归获取所有父级节点的前缀拼接串
	public static String getKeyPrifix(Element currentElement){
		String result = "";
		if(CommonUtil.isNotBlankObj(currentElement)){
			Element parentElement = currentElement.getParent();
			// 如果父级节点存在且不是根节点
			if(parentElement!=null && !parentElement.isRootElement()){
				result = parentElement.getName()+"." + result;
				if(!parentElement.isRootElement()){
					result = getKeyPrifix(parentElement) + "." + result;
				}
			}
		}
		if(CommonUtil.isNotBlank(result)){
			result = result.substring(result.startsWith(".")?1:0, result.endsWith(".")?result.length()-1:result.length());
		}
		return result;
	}
	
	public static String linefeed(String str){
		String resultStr = str==null?"":str;
		resultStr = resultStr + "\r\n";
		return resultStr;
	}
	
	/**
	 * 将字符串转换成小驼峰命名字符串
	 * @param resultStr
	 * @param flag 如果待解析字符串中不包含"_",flag=true则转换所有字符成小写,flag=false则之转换首字母为小写
	 * @return
	 */
	public static String parseToSmallHumpStr(String str,boolean flag){
		String resultStr = str;
		if(CommonUtil.isNotBlank(resultStr)){
			
			// 如果待解析字符串中包含"_",则只按照"_"后的转换成大写,其他字符全部小写
			if(resultStr.contains("_")){
				resultStr = resultStr.toLowerCase();
				String[] strArray = resultStr.split("_");
				if(strArray!=null && strArray.length>0){
					if(strArray.length>1){
						resultStr = strArray[0];
						for(int i=1;i<strArray.length;i++){
							if(strArray[i].length()>1){
								resultStr += strArray[i].substring(0,1).toUpperCase() + strArray[i].substring(1);
							}else{
								resultStr += strArray[i].substring(0,1);
							}
						}
					}else{
						resultStr = strArray[0];
					}
				}
				
			// 如果待解析字符串中不包含"_",则只转换首字母
			}else{
				if(flag){
					resultStr = resultStr.toLowerCase();
				}else{
					if(resultStr.length()>1){
						resultStr = resultStr.substring(0,1).toLowerCase() + resultStr.substring(1);
					}else{
						resultStr = resultStr.substring(0,1).toLowerCase();
					}
				}
			}
		}
		return resultStr;
	}
	
}
