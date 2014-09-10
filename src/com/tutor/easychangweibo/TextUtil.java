/**
 * Copyright 2009 Mark Wyszomierski
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.tutor.easychangweibo;
import java.util.Vector;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Typeface;
import android.view.KeyEvent;
  
public class TextUtil {  
  
    private int mTextPosx = 0;// x坐标  
    private int mTextPosy = 0;// y坐标  
    private int mTextWidth = 0;// 绘制宽度  
    private int mTextHeight = 0;// 绘制高度  
    private int mFontHeight = 0;// 绘制字体高度  
    private int mPageLineNum = 0;// 每一页显示的行数  
    private int mCanvasBGColor = 0;// 背景颜色  
    private int mFontColor = 0;// 字体颜色  
    private int mAlpha = 0;// Alpha值  
    private int mRealLine = 0;// 字符串真实的行数  
    private int mCurrentLine = 0;// 当前行  
    private int mTextSize = 0;// 字体大小  
    private String mStrText = "";  
    private Vector mString = null;  
    private Paint mPaint = null;  
    private Typeface mfonttype;
  
    public TextUtil(String StrText, int x, int y, int w, int h,  int alpha, int textsize,Typeface fonts) {  
        mPaint = new Paint();  
        mString = new Vector();  
        this.mStrText = StrText;  
        this.mTextPosx = x;  
        this.mTextPosy = y;  
        this.mTextWidth = w;  
        this.mTextHeight = h;  
//        this.mCanvasBGColor = bgcolor;  
        this.mFontColor = 0x535353;  
        this.mAlpha = alpha;  
        this.mTextSize = textsize;  
        this.mfonttype = fonts;
    }  
  
    public void InitText() {  
        mString.clear();// 清空Vector  
        // 对画笔属性的设置  
//      mPaint.setARGB(this.mAlpha, Color.red(this.mFontColor), Color  
//              .green(this.mFontColor), Color.blue(this.mFontColor));  
        
        mPaint.setTextSize(this.mTextSize);  
        mPaint.setColor(Color.BLACK);  
        mPaint.setTypeface(mfonttype);
        mPaint.setAntiAlias(true);  
        this.GetTextIfon();  
    }  
  
      
    public void GetTextIfon() {  
        char ch;  
        int w = 0;  
        int istart = 0;  
        FontMetrics fm = mPaint.getFontMetrics();// 得到系统默认字体属性  
        mFontHeight = (int) (Math.ceil(fm.descent - fm.top) );// 获得字体高度  
        mPageLineNum = mTextHeight / mFontHeight;// 获得行数  
        int count = this.mStrText.length();  
        for (int i = 0; i < count; i++) {  
            ch = this.mStrText.charAt(i);  
            float[] widths = new float[1];  
            String str = String.valueOf(ch);  
            mPaint.getTextWidths(str, widths);  
            if (ch == '\n') {  
                mRealLine++;// 真实的行数加一  
                mString.addElement(this.mStrText.substring(istart, i));  
                istart = i + 1;  
                w = 0;  
            } else {  
                w += (int) Math.ceil(widths[0]);  
                if (w > this.mTextWidth) {  
                    mRealLine++;// 真实的行数加一  
                    mString.addElement(this.mStrText.substring(istart, i));  
                    istart = i;  
                    i--;  
                    w = 0;  
                } else {  
                    if (i == count - 1) {  
                        mRealLine++;// 真实的行数加一  
                        mString.addElement(this.mStrText.substring(istart,  
                                count));  
                    }  
                }  
            }  
        }  
    }  
  
      
    public void DrawText(Canvas canvas) {  
        for (int i = this.mCurrentLine, j = 0; i < this.mRealLine; i++, j++) {  
            if (j > this.mPageLineNum) {  
                break;  
            }  
            canvas.drawText((String) (mString.elementAt(i)), this.mTextPosx,  
                    this.mTextPosy + this.mFontHeight * j, mPaint);  
        }  
    }  
      
    public boolean KeyDown(int keyCode, KeyEvent event)  
    {  
        if (keyCode == KeyEvent.KEYCODE_DPAD_UP)  
        {  
            if (this.mCurrentLine > 0)  
            {  
                this.mCurrentLine--;  
            }  
        }  
        else if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN)  
        {  
            if ((this.mCurrentLine + this.mPageLineNum) < (this.mRealLine - 1))  
            {  
                this.mCurrentLine++;  
            }  
        }  
        return false;  
    }  
}  