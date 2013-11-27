package tooltwist.anthony.widgets;

import tooltwist.ecommerce.AutomaticUrlParametersMode;
import tooltwist.ecommerce.RoutingUIM;
import tooltwist.wbd.CodeInserter;
import tooltwist.wbd.CodeInserterList;
import tooltwist.wbd.JavascriptCodeInserter;
import tooltwist.wbd.Navpoint;
import tooltwist.wbd.PageImportCodeInserter;
import tooltwist.wbd.SnippetParam;
import tooltwist.wbd.StylesheetCodeInserter;
import tooltwist.wbd.WbdCache;
import tooltwist.wbd.WbdException;
import tooltwist.wbd.WbdGenerator;
import tooltwist.wbd.WbdGenerator.GenerationMode;
import tooltwist.wbd.WbdNavPointProperty;
import tooltwist.wbd.WbdRenderHelper;
import tooltwist.wbd.WbdSession;
import tooltwist.wbd.WbdSizeInfo;
import tooltwist.wbd.WbdStringProperty;
import tooltwist.wbd.WbdWidget;
import tooltwist.wbd.WbdWidgetController;
import tooltwist.wbd.WbdProductionHelper;
//import tooltwist.anthony.productionHelpers.MenuWidgetProductionHelper;
import com.dinaa.ui.UimData;
import com.dinaa.ui.UimHelper;

/**
 * Menu Widget
 */
public class MenuWidgetWidget extends WbdWidgetController
{
	private static final String SNIPPET_PREVIEW = "menuWidget_preview.html";
	private static final String SNIPPET_DESIGN = "menuWidget_design.html";
	private static final String SNIPPET_PRODUCTION = "menuWidget_production.jsp";
	private static final boolean USE_PRODUCTION_HELPER = false;

	@Override
	protected void init(WbdWidget instance) throws WbdException
	{
		instance.defineProperty(new WbdStringProperty("elementId", null, "Id", ""));
//		instance.defineProperty(new WbdStringProperty("myProperty", null, "My Property", ""));
//		instance.defineProperty(new WbdNavPointProperty("navpoint", null, "Navpoint", ""));
	}
	
	@Override
	public void getCodeInserters(WbdGenerator generator, WbdWidget instance, UimData ud, CodeInserterList codeInserterList) throws WbdException
	{
//TODO: Uncomment this as required
		GenerationMode mode = generator.getMode();
		if (mode == GenerationMode.DESIGN)
		{
			// Add code inserters for design mode
			CodeInserter[] arr = {

//				// Include a CSS snippet
//				new StylesheetCodeInserter(instance.miscellaneousFilePath(generator, "menuWidget_cssHeader.css")),
			};
			codeInserterList.add(arr);
		}
		else if (mode == GenerationMode.PREVIEW)
		{
			// Add code inserters for preview mode
			CodeInserter[] arr = {
//				// Link to an external Javascript file
//				new JavascriptLinkInserter(jsUrl),

//				// Link to an external stylesheet
//				new StylesheetLinkInserter(cssUrl),

//				// Include a javascript snippet 
//				new JavascriptCodeInserter(instance.miscellaneousFilePath(generator, "menuWidget_jsHeader.js")),

				// Include a CSS snippet
				new StylesheetCodeInserter(generator,instance, "menuWidget_cssHeader.css"),
			};
			codeInserterList.add(arr);
		}
		else if (mode == GenerationMode.PRODUCTION || generator.getMode() == GenerationMode.CONTROLLER)
		{
			// Add code inserters for production mode
			CodeInserter[] arr = {
//				// Link to an external Javascript file
//				new JavascriptLinkInserter(jsUrl),
					
//				// Link to an external stylesheet
//				new StylesheetLinkInserter(cssUrl),
					
//				// Include a javascript snippet 
//				new JavascriptCodeInserter(instance.miscellaneousFilePath(generator, "menuWidget_jsHeader.js")),
					
				// Include a CSS snippet
				new StylesheetCodeInserter(generator,instance,"menuWidget_cssHeader.css"),

//				// Add import statements to the JSP
//				new PageImportCodeInserter(XData.class.getName()),
			};
			codeInserterList.add(arr);

			if (USE_PRODUCTION_HELPER)
			{
				SnippetParam[] productionHelperParams = null;
//				codeInserterList.add(WbdProductionHelper.codeInserter(instance, MenuWidgetProductionHelper.class.getName(), productionHelperParams));
//				codeInserterList.add(new PageImportCodeInserter(MenuWidgetProductionHelper.class.getName()));
			}
		}

	}
	
	@Override
	public String getLabel(WbdWidget instance) throws WbdException
	{
		return "Menu Widget";
	}
	
	@Override
	public WbdSizeInfo getSizeInfo(WbdGenerator generator, WbdWidget instance) throws WbdException
	{
		return WbdSizeInfo.unknownSizeInfo();
	}
	
	@Override
	public void renderForPreview(WbdGenerator generator, WbdWidget instance, UimData ud, WbdRenderHelper rh) throws WbdException
	{
		rh.renderSnippetForStaticPage(generator, instance, SNIPPET_PREVIEW, getSnippetParams(generator, instance, ud));
	}
	
	@Override
	public void renderForDesigner(WbdGenerator generator, WbdWidget instance, UimData ud, WbdRenderHelper rh) throws WbdException
	{
		rh.renderSnippetForStaticPage(generator, instance, SNIPPET_DESIGN, getSnippetParams(generator, instance, ud));
	}
	
	@Override
	public void renderForJSP(WbdGenerator generator, WbdWidget instance, UimHelper ud, WbdRenderHelper rh) throws Exception
	{
//		rh.beforeProductionCode(generator, instance, getSnippetParams(generator, instance, ud), USE_PRODUCTION_HELPER);
//		rh.renderSnippetForProduction(generator, instance, SNIPPET_PRODUCTION);
//		rh.afterProductionCode(generator, instance);
		
		rh.append("<div id='cssmenu'>");
		rh.append("<ul>");
		Navpoint menuNav = WbdCache.findNavPoint("ttdemo-13", true);
		
		
		String currentNavpoint = WbdSession.getNavpointId(ud.getCredentials());
		
		for (Navpoint nav: menuNav.getChildren()) {
			String navId = nav.getId();
			//construct full url of navpoint
			String navpointUrl = RoutingUIM.navpointUrl(ud, navId, AutomaticUrlParametersMode.NO_AUTOMATIC_URL_PARAMETERS);
			String label = nav.getLabel();
			String activeClass = "";
			if (navId.equals(currentNavpoint) )
			{
				activeClass = "active";
			}
			boolean hasChild = nav.hasChildren(true);
			rh.append("<li class="+activeClass+"><a href="+navpointUrl+"><span>"+label+"</span></a>");
			generateSub(hasChild, rh, nav, navpointUrl,ud);
			rh.append("</li>");
			
		}
		rh.append("</ul>");
		
	}
	
	public void generateSub(boolean hasChild, WbdRenderHelper rh, Navpoint nav, String navpointUrl,UimData ud)
	{
		String currentNavpoint = WbdSession.getNavpointId(ud.getCredentials());
		if (hasChild)
		{
			rh.append("<ul>");
			for (Navpoint subNav: nav.getChildren()) {
				String subnavId = subNav.getId();
				String sublabel = subNav.getLabel();
				rh.append("<li class='has-sub'><a href="+navpointUrl+"><span>"+sublabel+"</span></a>");
				generateSub(subNav.hasChildren(true),rh,subNav,navpointUrl,ud);
				rh.append("</li>");
		}
			rh.append("</ul>");
		
		}
	}
	
	private SnippetParam[] getSnippetParams(WbdGenerator generator, WbdWidget instance, UimData ud) throws WbdException {
//		String myProperty = instance.getProperty("myProperty", null);
//		String myNavpoint = instance.getProperty("myNavpoint", null);
		SnippetParam[] params = {
//			new SnippetParam("myProperty", myProperty),
//			new SnippetParam("myNavpoint", myNavpoint)
		};
		return params;
	}
}
