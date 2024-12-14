// Mocks generated by Mockito 5.4.4 from annotations
// in nfc_sync/test/bloc/quote_bloc_test.dart.
// Do not manually edit this file.

// ignore_for_file: no_leading_underscores_for_library_prefixes
import 'dart:async' as _i4;

import 'package:mockito/mockito.dart' as _i1;
import 'package:nfc_sync/dao/quote_dao.dart' as _i6;
import 'package:nfc_sync/models/quote.dart' as _i2;
import 'package:nfc_sync/services/nfc_service.dart' as _i5;
import 'package:nfc_sync/services/quote_service.dart' as _i3;

// ignore_for_file: type=lint
// ignore_for_file: avoid_redundant_argument_values
// ignore_for_file: avoid_setters_without_getters
// ignore_for_file: comment_references
// ignore_for_file: deprecated_member_use
// ignore_for_file: deprecated_member_use_from_same_package
// ignore_for_file: implementation_imports
// ignore_for_file: invalid_use_of_visible_for_testing_member
// ignore_for_file: prefer_const_constructors
// ignore_for_file: unnecessary_parenthesis
// ignore_for_file: camel_case_types
// ignore_for_file: subtype_of_sealed_class

class _FakeQuote_0 extends _i1.SmartFake implements _i2.Quote {
  _FakeQuote_0(
    Object parent,
    Invocation parentInvocation,
  ) : super(
          parent,
          parentInvocation,
        );
}

/// A class which mocks [QuoteService].
///
/// See the documentation for Mockito's code generation for more information.
class MockQuoteService extends _i1.Mock implements _i3.QuoteService {
  MockQuoteService() {
    _i1.throwOnMissingStub(this);
  }

  @override
  _i4.Future<_i2.Quote> getRandomQuote() => (super.noSuchMethod(
        Invocation.method(
          #getRandomQuote,
          [],
        ),
        returnValue: _i4.Future<_i2.Quote>.value(_FakeQuote_0(
          this,
          Invocation.method(
            #getRandomQuote,
            [],
          ),
        )),
      ) as _i4.Future<_i2.Quote>);
}

/// A class which mocks [NFCService].
///
/// See the documentation for Mockito's code generation for more information.
class MockNFCService extends _i1.Mock implements _i5.NFCService {
  MockNFCService() {
    _i1.throwOnMissingStub(this);
  }

  @override
  _i4.Future<bool> isNFCAvailable() => (super.noSuchMethod(
        Invocation.method(
          #isNFCAvailable,
          [],
        ),
        returnValue: _i4.Future<bool>.value(false),
      ) as _i4.Future<bool>);

  @override
  _i4.Future<void> writeQuoteTag() => (super.noSuchMethod(
        Invocation.method(
          #writeQuoteTag,
          [],
        ),
        returnValue: _i4.Future<void>.value(),
        returnValueForMissingStub: _i4.Future<void>.value(),
      ) as _i4.Future<void>);

  @override
  _i4.Future<bool> listenForQuoteTag() => (super.noSuchMethod(
        Invocation.method(
          #listenForQuoteTag,
          [],
        ),
        returnValue: _i4.Future<bool>.value(false),
      ) as _i4.Future<bool>);
}

/// A class which mocks [QuoteDao].
///
/// See the documentation for Mockito's code generation for more information.
class MockQuoteDao extends _i1.Mock implements _i6.QuoteDao {
  MockQuoteDao() {
    _i1.throwOnMissingStub(this);
  }

  @override
  _i4.Future<void> insertQuote(_i2.Quote? quote) => (super.noSuchMethod(
        Invocation.method(
          #insertQuote,
          [quote],
        ),
        returnValue: _i4.Future<void>.value(),
        returnValueForMissingStub: _i4.Future<void>.value(),
      ) as _i4.Future<void>);

  @override
  _i4.Future<void> deleteQuote(_i2.Quote? quote) => (super.noSuchMethod(
        Invocation.method(
          #deleteQuote,
          [quote],
        ),
        returnValue: _i4.Future<void>.value(),
        returnValueForMissingStub: _i4.Future<void>.value(),
      ) as _i4.Future<void>);

  @override
  _i4.Stream<List<_i2.Quote>> findAllQuotes() => (super.noSuchMethod(
        Invocation.method(
          #findAllQuotes,
          [],
        ),
        returnValue: _i4.Stream<List<_i2.Quote>>.empty(),
      ) as _i4.Stream<List<_i2.Quote>>);
}
