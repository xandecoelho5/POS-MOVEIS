import 'package:flutter/cupertino.dart';
import 'package:nfc_sync/dao/quote_dao.dart';
import 'package:nfc_sync/models/quote.dart';
import 'package:nfc_sync/services/nfc_service.dart';
import 'package:nfc_sync/services/quote_service.dart';

class QuoteProvider with ChangeNotifier {
  bool _isLoading = false;
  Quote? _currentQuote;
  String? _error;

  Quote? get currentQuote => _currentQuote;

  bool get isLoading => _isLoading;

  String? get error => _error;

  final QuoteService _quoteService;
  final NFCService _nfcService;
  final QuoteDao _quoteDao;

  QuoteProvider(this._quoteService, this._nfcService, this._quoteDao);

  Future<void> setupAutoNFCQuote() async {
    try {
      while (true) {
        final isQuoteTag = await _nfcService.listenForQuoteTag();
        if (isQuoteTag) {
          _currentQuote = null;
          _isLoading = true;
          _error = null;
          notifyListeners();

          _currentQuote = await _quoteService.getRandomQuote();
          if (_currentQuote != null) {
            await _quoteDao.insertQuote(_currentQuote!);
          }
          _isLoading = false;
          notifyListeners();
        } else {
          _error = 'Not a quote tag';
          notifyListeners();
        }
      }
    } catch (e) {
      _error = e.toString();
      _isLoading = false;
      notifyListeners();
    }
  }
}
