package com.eaugusto.investorpdf.model.clear;

import java.util.List;

public class SectionMapper {

	public enum FindMode {
		START_WITH, CONTAINS
	}
	
	private String sectionName;
	private String startSectionKey;
	private boolean forwardDirection;
	private Integer lineCount;
	private String endSectionKey;
	private List<String> sectionContent;
	private String findLine;
	private FindMode findMode;
	
	public SectionMapper(String sectionName, String startSectionKey, boolean forwardDirection, Integer lineCount, String endSectionKey, String findLine, FindMode findMode) {
		this.sectionName = sectionName;
		this.startSectionKey = startSectionKey;
		this.forwardDirection = forwardDirection;
		this.lineCount = lineCount;
		this.endSectionKey = endSectionKey;
		this.findLine = findLine;
		this.findMode = findMode;
	}
	
	public SectionMapper(String startSectionKey, boolean forwardDirection, Integer lineCount, String endSectionKey) {
		this(startSectionKey, startSectionKey, forwardDirection, lineCount, endSectionKey, null, null);
	}
	
	public SectionMapper(String startSectionKey, boolean forwardDirection, Integer lineCount) {
		this(startSectionKey, startSectionKey, forwardDirection, lineCount, null, null, null);
	}
	
	public SectionMapper(String startSectionKey, boolean forwardDirection, String endSectionKey) {
		this(startSectionKey, startSectionKey, forwardDirection, null, endSectionKey, null, null);
	}
	
	public SectionMapper(FindMode mode, String lineStart) {
		this(null, null, true, null, null, lineStart, mode);
	}

	public String getStartSectionKey() {
		return startSectionKey;
	}
	
	public void setStartSectionKey(String startSectionKey) {
		this.startSectionKey = startSectionKey;
	}
	
	public boolean isForwardDirection() {
		return forwardDirection;
	}
	
	public void setForwardDirection(boolean forwardDirection) {
		this.forwardDirection = forwardDirection;
	}
	
	public Integer getLineCount() {
		return lineCount;
	}
	
	public void setLineCount(Integer lineCount) {
		this.lineCount = lineCount;
	}
	
	public String getEndSectionKey() {
		return endSectionKey;
	}
	
	public void setEndSectionKey(String endSectionKey) {
		this.endSectionKey = endSectionKey;
	}

	public String getSectionName() {
		return sectionName;
	}

	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}

	public List<String> getSectionContent() {
		return sectionContent;
	}

	public void setSectionContent(List<String> sectionContent) {
		this.sectionContent = sectionContent;
	}

	public String getFindLine() {
		return findLine;
	}

	public void setFindLine(String findLine) {
		this.findLine = findLine;
	}
	
	public FindMode getFindMode() {
		return findMode;
	}

	public void setFindMode(FindMode findMode) {
		this.findMode = findMode;
	}

	@Override
	public String toString() {
		return "SectionMapper [sectionName=" + sectionName + ", startSectionKey=" + startSectionKey
				+ ", forwardDirection=" + forwardDirection + ", lineCount=" + lineCount + ", endSectionKey="
				+ endSectionKey + ", sectionContent=" + sectionContent + ", findLine=" + findLine + ", findMode="
				+ findMode + "]";
	}
}