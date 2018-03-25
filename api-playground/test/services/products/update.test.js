'use strict';

const assert = require('assert');
const app = require('../../../src/app');
var request = require('supertest');
// var productSchema = require('../../../src/services/products/schema');

describe('products update', function () {
  beforeEach(function (done) {
    app.set('readonly', false);
    done();
  });

  it('sucessfully updates the name of an existing product', function (done) {
    request(app)
      .post('/products')
      .send({
        name: 'Test Product Ale',
        description: 'This is a test product',
        upc: '12345',
        type: 'Electronics',
        model: 'Product0123'
      })
      .expect(201)
      .end(function (err, result) {
        if (err) { assert.ifError(err); }
        var id = result.body.id;

        assert.ok(id);

        request(app)
          .patch('/products/' + id)
          .send({
            name: 'Test Product Ale updated',
            description: 'This is a test product',
            upc: '12345',
            type: 'Electronics',
            model: 'Product0123'
          })
          .expect(200)
          .end(function (err, result) {
            if (err) { assert.ifError(err); }
            var newProductName = result.body.name;

            assert.equal(newProductName, 'Test Product Ale updated');
            done();
          });
      });
  });

  it('does not allow updating a product with empty body', function(done) {
    request(app)
      .post('/products')
      .send({
        name: 'Test Product Update with empty body',
        description: 'This is a test product',
        upc: '12345',
        type: 'Electronics',
        model: 'Product0123'
      })
      .expect(201)
      .end(function (err, result) {
        if (err) { assert.ifError(err); }
        var id = result.body.id;

        assert.ok(id);

        request(app)
          .patch('/products/' + id)
          .expect(400)
          .end(function (err, result) {
            console.log(result);
            if (err) { assert.ifError(err); }
            done();
          });
      });
  });

});
