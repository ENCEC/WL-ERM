package com.gillion.service.impl;

import com.gillion.ds.excel.export.model.dto.IExportFieldInformation;
import com.gillion.ds.excel.export.service.SheetCellStyleProviderService;
import com.gillion.ds.inout.config.ExcelCellStylesProperties;
import com.gillion.ec.core.utils.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Function;

/**
 * @author zengqw
 * @email zengqw@gillion.com.cn
 * @date 2020/5/27
 * @time 15:29
 * @description:
 */
@Service("sheetCellStyleProviderService")
public class SheetCellStyleProviderServiceImpl implements SheetCellStyleProviderService {
    @Override
    public Function<Workbook, Optional<CellStyle>> getHeaderStyleProvider(IExportFieldInformation iExportFieldInformation) {
        return wf -> {
            final CellStyle cs = wf.createCellStyle();
            setCellBorderStyle(cs);
            final Font font = wf.createFont();
            font.setBold(true);
            cs.setFont(font);

            final DataFormat format = wf.createDataFormat();
            cs.setDataFormat(format.getFormat("@"));
            return Optional.of(cs);
        };
    }

    @Override
    public Function<Workbook, Optional<CellStyle>> getColumnStyleProvider(IExportFieldInformation iExportFieldInformation) {
        if (StringUtils.isNotEmpty(iExportFieldInformation.getFormatPattern())) {
            return wf -> {
                final CellStyle cs = wf.createCellStyle();
                setBackGroudStyle(cs);

                final DataFormat format = wf.createDataFormat();
                cs.setDataFormat(format.getFormat(iExportFieldInformation.getFormatPattern()));
                return Optional.of(cs);
            };
        } else {
            return wf -> {
                final CellStyle cs = wf.createCellStyle();
                setCellBorderStyle(cs);

                final DataFormat format = wf.createDataFormat();
                cs.setDataFormat(format.getFormat("@"));
                return Optional.of(cs);
            };
        }
    }

    private void setCellBorderStyle(CellStyle cs) {
        final BorderStyle borderStyle = ExcelCellStylesProperties.getInstance().getBorderStyle();
        if (borderStyle == BorderStyle.NONE) {
            return;
        }
        cs.setBorderLeft(borderStyle);
        cs.setBorderTop(borderStyle);
        cs.setBorderRight(borderStyle);
        cs.setBorderBottom(borderStyle);

    }

    private void setBackGroudStyle(CellStyle cs) {

        cs.setFillBackgroundColor(Short.parseShort("500"));

    }
}
