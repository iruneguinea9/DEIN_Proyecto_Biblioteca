/*--------------------------------------------------------------------------------------------------------------- 
 	[PROYECTO DEIN 2EV - Irune Guinea Zufiaurre]
    Modelo: HELP
    --------------------------------------------------------------------------------------------------------------- */
package model;

public class Help {
	private String text;
	private String html;
	private boolean local = true;

	public Help(String text, String html) {
		this.text = text;
		this.html = html;
	}

	public Help(String text, String html, boolean local) {
		this.text = text;
		this.html = html;
		this.local = local;
	}

	public String getText() {
		return text;
	}

	public String getHtml() {
		return html;
	}

	public boolean isLocal() {
		return local;
	}

	@Override
	public String toString() {
		return text;
	}
}
