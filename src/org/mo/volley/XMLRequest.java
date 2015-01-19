package org.mo.volley;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.io.UnsupportedEncodingException;

/**
 * Created by moziqi on 2015/1/19 0019.
 * XMLRequest request = new XMLRequest("http://flash.weather.com.cn/wmaps/xml/china.xml",
 * new Response.Listener<XmlPullParser>() {
 *
 * @Override public void onResponse(XmlPullParser response) {
 * try {
 * int eventType = response.getEventType();
 * while (eventType != XmlPullParser.END_DOCUMENT) {
 * switch (eventType) {
 * case XmlPullParser.START_TAG:
 * String nodeName = response.getName();
 * if ("city".equals(nodeName)) {
 * String pName = response.getAttributeValue(0);
 * Log.d("TAG", "pName is " + pName);
 * }
 * break;
 * }
 * eventType = response.next();
 * }
 * } catch (XmlPullParserException e) {
 * e.printStackTrace();
 * } catch (IOException e) {
 * e.printStackTrace();
 * }
 * }
 * },
 * new Response.ErrorListener() {
 * @Override public void onErrorResponse(VolleyError error) {
 * Log.e("TAG", error.getMessage(), error);
 * }
 * });
 * mQueue.add(request);
 */
public class XMLRequest extends Request<XmlPullParser> {

    private final Response.Listener<XmlPullParser> mListener;

    public XMLRequest(int method, String url, Response.Listener<XmlPullParser> mListener, Response.ErrorListener listener) {
        super(method, url, listener);
        this.mListener = mListener;
    }

    public XMLRequest(String url, Response.Listener<XmlPullParser> mListener, Response.ErrorListener listener) {
        this(Method.GET, url, mListener, listener);
    }

    @Override
    protected Response<XmlPullParser> parseNetworkResponse(NetworkResponse response) {
        try {
            String xmlString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = factory.newPullParser();
            xmlPullParser.setInput(new StringReader(xmlString));
            return Response.success(xmlPullParser, HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (XmlPullParserException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(XmlPullParser response) {
        mListener.onResponse(response);
    }
}
