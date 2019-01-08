package com.eaugusto.investorpdf.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import com.eaugusto.investorpdf.api.BrokerReport;
import com.eaugusto.investorpdf.model.Broker;
import com.eaugusto.investorpdf.model.clear.SectionMapper;

public class BrokerParser {

	public static BrokerReport getReport(String text, Broker broker) {
		BrokerReport brokerReport = broker.getBrokerSupplier().get();

		for(Entry<String, SectionMapper> entry : brokerReport.getSections().entrySet()) {
			SectionMapper sec = entry.getValue();

			if(sec.getStartSectionKey() != null) {
				int sectionIndex = text.indexOf(sec.getStartSectionKey());

				List<String> contentList = new ArrayList<String>();
				if(sec.isForwardDirection()) {
					int startContentIndex = sectionIndex + (sec.getStartSectionKey() + "\n").length() + 1;

					for(int i = 0; i < sec.getLineCount(); i++) {
						int endLineIndex = text.indexOf("\n", startContentIndex);
						contentList.add(text.substring(startContentIndex, endLineIndex).trim());

						startContentIndex = endLineIndex;
					}
					sec.setSectionContent(contentList);
				} else {
					int endLineIndex = text.lastIndexOf("\n", sectionIndex);

					for(int i = 0; i < sec.getLineCount(); i++) {
						int startContentIndex = text.lastIndexOf("\n", endLineIndex - 1);
						contentList.add(text.substring(startContentIndex, endLineIndex).trim());

						endLineIndex = startContentIndex;
					}
					sec.setSectionContent(contentList);
				}
			} else if(sec.getFindLine() != null) {
				String[] lines = text.split("\r\n");
				List<String> linesFilter = Arrays.stream(lines).filter(l -> {
					if(sec.getFindMode() == SectionMapper.FindMode.START_WITH) {
						return l.startsWith(sec.getFindLine());
					} else if(sec.getFindMode() == SectionMapper.FindMode.CONTAINS) {
						return l.contains(sec.getFindLine());
					} else {
						return false;
					}
				}).collect(Collectors.toList());
				sec.setSectionContent(linesFilter);
			}
		}

		return brokerReport;
	}
}