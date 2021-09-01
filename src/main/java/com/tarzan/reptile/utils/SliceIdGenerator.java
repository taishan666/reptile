package com.tarzan.reptile.utils;

import cn.hutool.json.JSONUtil;

/**
 * 生成slice_id的工具类
 * 每个转写任务都新建一个SliceIdGenerator来按照分片顺序依次生成slice_id
 * 
 * @author white
 *
 */
public class SliceIdGenerator {

    private static final String INIT_STR = "aaaaaaaaa`";
	private int length = 0;
	private char[] ch;

	public SliceIdGenerator() {
		this.length = INIT_STR.length();
		this.ch = INIT_STR.toCharArray();
	}

	/**
	 * 获取sliceId
	 * 
	 * @return
	 */
	public String getNextSliceId() {
		for (int i = 0, j = length - 1; i < length && j >= 0; i++) {
			if (ch[j] != 'z') {
				ch[j]++;
				break;
			} else {
				ch[j] = 'a';
				j--;
				continue;
			}
		}

		return new String(ch);
	}

	public static void main(String[] args) {
		SliceIdGenerator generator = new SliceIdGenerator();
		System.out.println(generator.getNextSliceId());
	}
}